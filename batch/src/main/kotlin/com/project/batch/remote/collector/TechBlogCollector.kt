package com.project.batch.remote.collector

import com.project.batch.constants.BlogSource
import com.project.batch.domain.TechBlog
import com.project.batch.remote.collector.strategy.BlogCollectorStrategy
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TechBlogCollector(
    private val collectors: List<BlogCollectorStrategy>,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun collectAll(sources: List<BlogSource>): List<TechBlog> =
        coroutineScope {
            sources.map { source ->
                async {
                    runCatching {
                        collectors.firstOrNull { it.supports(source.type) }
                            ?.collect(source)
                            ?: emptyList()
                    }
                        .onFailure { log.error("Failed to collect from ${source.displayName}", it) }
                        .getOrElse { emptyList() }
                }
            }.awaitAll().flatten()
        }
}