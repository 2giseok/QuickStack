package com.project.batch.remote.collector

import com.project.batch.constants.Source
import com.project.batch.domain.TechBlog
import com.project.batch.remote.strategy.BlogCollectorStrategy
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class TechBlogCollector(
    private val collectors: List<BlogCollectorStrategy>,
) {
    companion object {
        private val log = LoggerFactory.getLogger(TechBlogCollector::class.java)
    }

    suspend fun collectAll(sources: List<Source>): List<TechBlog> =
        supervisorScope {
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