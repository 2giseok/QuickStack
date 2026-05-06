package com.project.batch.task

import com.project.batch.constants.Source
import com.project.batch.fixture.TestFixtures
import com.project.batch.remote.collector.TechBlogCollector
import com.project.batch.repository.TechBlogRepository
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

class TechBlogTaskTest {

    private val collector = mockk<TechBlogCollector>()
    private val repository = mockk<TechBlogRepository>()
    private val notificationService = mockk<NotificationService>()
    private val transactionalOperator = mockk<TransactionalOperator> {
        every { execute(any<TransactionCallback<Any>>()) } answers {
            Flux.from(firstArg<TransactionCallback<Any>>().doInTransaction(mockk(relaxed = true)))
        }
    }
    private val clock = Clock.fixed(Instant.parse("2026-04-19T00:00:00Z"), ZoneId.of("Asia/Seoul"))

    private val task = TechBlogTask(collector, repository, notificationService, transactionalOperator, clock)

    @Test
    fun `오늘 발행된 블로그가 있으면 알림을 발송한다`() = runTest {
        val todayBlogs = listOf(TestFixtures.techBlog())
        coEvery { collector.collectAll(any()) } returns listOf(TestFixtures.techBlog())
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectBlogsPublishedToday(any()) } returns todayBlogs
        coJustRun { notificationService.sendTechBlogNotification(any(), any()) }

        task.collectTechBlogs()

        coVerify(exactly = 1) { notificationService.sendTechBlogNotification(todayBlogs, LocalDate.now(clock)) }
    }

    @Test
    fun `오늘 발행된 블로그가 없으면 알림을 발송하지 않는다`() = runTest {
        coEvery { collector.collectAll(any()) } returns emptyList()
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectBlogsPublishedToday(any()) } returns emptyList()

        task.collectTechBlogs()

        coVerify(exactly = 0) { notificationService.sendTechBlogNotification(any(), any()) }
    }

    @Test
    fun `컬렉터가 빈 리스트를 반환해도 bulkInsert와 조회는 실행된다`() = runTest {
        coEvery { collector.collectAll(any()) } returns emptyList()
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectBlogsPublishedToday(any()) } returns emptyList()

        task.collectTechBlogs()

        coVerify(exactly = 1) { repository.bulkInsert(emptyList()) }
        coVerify(exactly = 1) { repository.selectBlogsPublishedToday(LocalDate.now(clock)) }
    }

    @Test
    fun `Source 전체를 수집 대상으로 넘긴다`() = runTest {
        coEvery { collector.collectAll(Source.entries) } returns emptyList()
        coJustRun { repository.bulkInsert(any()) }
        coEvery { repository.selectBlogsPublishedToday(any()) } returns emptyList()

        task.collectTechBlogs()

        coVerify(exactly = 1) { collector.collectAll(Source.entries) }
    }
}
