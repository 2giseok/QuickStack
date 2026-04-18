package com.project.batch.task

import com.project.batch.aop.LogExecutionTime
import com.project.batch.constants.TechStack
import com.project.batch.remote.collector.GithubReleaseCollector
import com.project.batch.repository.GithubReleaseRepository
import com.project.batch.service.NotificationService
import kotlinx.coroutines.supervisorScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.LocalDate

@Component
class GithubReleaseTask(
    private val githubReleaseCollector: GithubReleaseCollector,
    private val githubReleaseRepository: GithubReleaseRepository,
    private val notificationService: NotificationService,
    private val clock: Clock,
) {

    @LogExecutionTime
    @Scheduled(cron = "0 */2 * * * *", zone = "Asia/Seoul")
    suspend fun collectGithubReleases() = supervisorScope {
        val releases = githubReleaseCollector.collectAllReleases(TechStack.entries)
        githubReleaseRepository.bulkInsert(releases)

        val today = LocalDate.now(clock)
        val todayReleases = githubReleaseRepository.selectReleaseTodayPublished(today)
        if (todayReleases.isNotEmpty()) {
            notificationService.sendReleaseNotification(todayReleases, today)
        }
    }
}