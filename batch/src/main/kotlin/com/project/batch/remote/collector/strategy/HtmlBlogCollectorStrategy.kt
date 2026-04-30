package com.project.batch.remote.collector.strategy

import com.project.batch.config.BlogCrawlRegistry
import com.project.batch.constants.BlogSource
import com.project.batch.constants.CollectionType
import com.project.batch.domain.TechBlog
import com.project.batch.utils.Snowflake
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Component
class HtmlBlogCollectorStrategy(
    @Qualifier("commonWebClient") private val client: WebClient,
    private val snowflake: Snowflake,
) : BlogCollectorStrategy {

    companion object {
        private const val USER_AGENT =
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36"
    }

    override fun supports(type: CollectionType): Boolean = type == CollectionType.HTML

    override suspend fun collect(source: BlogSource): List<TechBlog> {
        val config = BlogCrawlRegistry[source] ?: return emptyList()

        val html = client.get()
            .uri(source.url)
            .header(HttpHeaders.USER_AGENT, USER_AGENT)
            .retrieve()
            .bodyToMono<String>()
            .awaitSingleOrNull() ?: return emptyList()

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