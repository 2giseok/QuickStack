package com.project.batch.remote.collector.strategy

import com.project.batch.config.BlogCrawlRegistry
import com.project.batch.config.HtmlCrawlSpec
import com.project.batch.constants.BlogSource
import com.project.batch.constants.CollectionType
import com.project.batch.domain.TechBlog
import com.project.batch.utils.Snowflake
import com.project.batch.remote.browser.PlaywrightBrowserManager
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class PlaywrightBlogCollectorStrategy(
    private val browserManager: PlaywrightBrowserManager,
    private val snowflake: Snowflake,
) : BlogCollectorStrategy {

    override fun supports(type: CollectionType): Boolean = type == CollectionType.PLAYWRIGHT

    override suspend fun collect(source: BlogSource): List<TechBlog> {
        val config = BlogCrawlRegistry[source] ?: return emptyList()
        val html = browserManager.fetchHtml(source.url) ?: return emptyList()
        return parseHtml(html, source, config)
    }

    private fun parseHtml(html: String, source: BlogSource, config: HtmlCrawlSpec): List<TechBlog> {
        val doc = Jsoup.parse(html, source.url)
        return doc.select(config.listSelector).mapNotNull { element ->
            val title = element.select(config.titleSelector).text().takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val url = element.select(config.linkSelector).attr("abs:href").takeIf { it.isNotBlank() } ?: return@mapNotNull null
            val dateStr = if (config.dateAttribute == "text") {
                element.select(config.dateSelector).text()
            } else {
                element.select(config.dateSelector).attr(config.dateAttribute)
            }
            val publishedAt = parseDate(dateStr) ?: return@mapNotNull null
            TechBlog(
                id = snowflake.nextId(),
                source = source.name,
                region = source.region,
                title = title,
                url = url,
                publishedAt = publishedAt,
            )
        }
    }

    private fun parseDate(dateStr: String): Instant? {
        if (dateStr.isBlank()) return null
        return runCatching { Instant.parse(dateStr) }
            .recoverCatching { ZonedDateTime.parse(dateStr).toInstant() }
            .recoverCatching {
                LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern("yyyy.MM.dd"))
                    .atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant()
            }
            .recoverCatching {
                LocalDate.parse(dateStr.trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant()
            }
            .getOrNull()
    }
}