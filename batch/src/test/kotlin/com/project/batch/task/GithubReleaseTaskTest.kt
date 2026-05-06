package com.project.batch.task

import com.project.batch.constants.TechStack
import com.project.batch.fixture.TestFixtures
import com.project.batch.remote.collector.GithubReleaseCollector
import com.project.batch.repository.GithubReleaseRepository
import com.project.batch.service.NotificationService
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.springframework.transaction.reactive.TransactionCallback
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Flux
import java.time.Clock
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class GithubReleaseTaskTest {

    private val collector = mockk<GithubReleaseCollector>()
    private val repository = mockk<GithubReleaseRepository>()
    private val notificationService = mockk<NotificationService>()
    private val transactionalOperator = mockk<TransactionalOperator> {
        every { execute(any<TransactionCallback<Any>>()) } answers {
            Flux.from(firstArg<TransactionCallback<Any>>().doInTransaction(mockk(relaxed = true)))
        }
    }
    private val clock = Clock.fixed(Instant.parse("2026-04-18T00:00:00Z"), ZoneId.of("Asia/Seoul"))

    private val task = GithubReleaseTask(collector, repository, notificationService, transactionalOperator, clock)

    @Test
    fun `오늘 발행된 릴리즈가 있으면 알림을 발송한다`() = runTest {
        val todayReleases = listOf(TestFixtures.githubRelease())
        coEvery { collector.collectAll(any()) } returns listOf(TestFixtures.githubRelease())
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectReleaseTodayPublished(any()) } returns todayReleases
        coJustRun { notificationService.sendReleaseNotification(any(), any()) }

        task.collectGithubReleases()

        coVerify(exactly = 1) { notificationService.sendReleaseNotification(todayReleases, LocalDate.now(clock)) }
    }

    @Test
    fun `오늘 발행된 릴리즈가 없으면 알림을 발송하지 않는다`() = runTest {
        coEvery { collector.collectAll(any()) } returns emptyList()
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectReleaseTodayPublished(any()) } returns emptyList()

        task.collectGithubReleases()

        coVerify(exactly = 0) { notificationService.sendReleaseNotification(any(), any()) }
    }

    @Test
    fun `컬렉터가 빈 리스트를 반환해도 bulkInsert와 조회는 실행된다`() = runTest {
        coEvery { collector.collectAll(any()) } returns emptyList()
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectReleaseTodayPublished(any()) } returns emptyList()

        task.collectGithubReleases()

        coVerify(exactly = 1) { repository.bulkInsert(emptyList()) }
        coVerify(exactly = 1) { repository.selectReleaseTodayPublished(LocalDate.now(clock)) }
    }

    @Test
    fun `TechStack 전체를 수집 대상으로 넘긴다`() = runTest {
        coEvery { collector.collectAll(TechStack.entries) } returns emptyList()
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectReleaseTodayPublished(any()) } returns emptyList()

        task.collectGithubReleases()

        coVerify(exactly = 1) { collector.collectAll(TechStack.entries) }
    }
}
