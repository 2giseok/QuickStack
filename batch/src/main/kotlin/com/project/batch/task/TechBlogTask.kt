package com.project.batch.task

import com.project.batch.aop.LogExecutionTime
import com.project.batch.constants.BlogSource
import com.project.batch.remote.collector.TechBlogCollector
import com.project.batch.repository.TechBlogRepository
import kotlinx.coroutines.supervisorScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TechBlogTask(
    private val techBlogCollector: TechBlogCollector,
    private val techBlogRepository: TechBlogRepository,
) {

    @LogExecutionTime
    @Scheduled(cron = "0 */2 * * * *", zone = "Asia/Seoul")
    suspend fun collectTechBlogs() = supervisorScope {
        val blogs = techBlogCollector.collectAll(BlogSource.entries)
        techBlogRepository.bulkInsert(blogs)
    }
}