package com.project.batch.remote.collector

import com.project.batch.constants.CollectionType
import com.project.batch.constants.Source
import com.project.batch.fixture.TestFixtures
import com.project.batch.remote.strategy.BlogCollectorStrategy
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class TechBlogCollectorTest {

    private val rssStrategy = mockk<BlogCollectorStrategy>()
    private val htmlStrategy = mockk<BlogCollectorStrategy>()
    private val collector = TechBlogCollector(listOf(rssStrategy, htmlStrategy))

    @Test
    fun `수집된 블로그가 하나의 리스트로 합쳐서 반환된다`() = runTest {
        every { rssStrategy.supports(CollectionType.RSS) } returns true
        every { rssStrategy.supports(CollectionType.HTML) } returns false
        coEvery { rssStrategy.collect(Source.KAKAO) } returns listOf(
            TestFixtures.techBlog(title = "카카오 블로그 1"),
            TestFixtures.techBlog(title = "카카오 블로그 2"),
        )
        every { htmlStrategy.supports(CollectionType.HTML) } returns true
        every { htmlStrategy.supports(CollectionType.RSS) } returns false
        coEvery { htmlStrategy.collect(Source.NAVER_D2) } returns listOf(
            TestFixtures.techBlog(title = "네이버 블로그 1"),
        )

        val result = collector.collectAll(listOf(Source.KAKAO, Source.NAVER_D2))

        assertThat(result).hasSize(3)
    }

    @Test
    fun `지원하는 전략이 없는 source는 빈 리스트로 처리된다`() = runTest {
        every { rssStrategy.supports(any()) } returns false
        every { htmlStrategy.supports(any()) } returns false

        val result = collector.collectAll(listOf(Source.KAKAO))

        assertThat(result).isEmpty()
    }

    @Test
    fun `수집 중 예외가 발생한 source는 빈 리스트로 처리되고 나머지는 정상 수집된다`() = runTest {
        every { rssStrategy.supports(CollectionType.RSS) } returns true
        every { rssStrategy.supports(CollectionType.HTML) } returns false
        coEvery { rssStrategy.collect(Source.KAKAO) } throws RuntimeException("연결 실패")
        coEvery { rssStrategy.collect(Source.KAKAO_PAY) } returns listOf(TestFixtures.techBlog(title = "카카오페이 블로그"))
        every { htmlStrategy.supports(any()) } returns false

        val result = collector.collectAll(listOf(Source.KAKAO, Source.KAKAO_PAY))

        assertThat(result).hasSize(1)
        assertThat(result[0].title).isEqualTo("카카오페이 블로그")
    }

    @Test
    fun `모든 source 수집 실패 시 빈 리스트를 반환한다`() = runTest {
        every { rssStrategy.supports(any()) } returns true
        every { htmlStrategy.supports(any()) } returns false
        coEvery { rssStrategy.collect(any()) } throws RuntimeException("전체 실패")

        val result = collector.collectAll(listOf(Source.KAKAO, Source.KAKAO_PAY))

        assertThat(result).isEmpty()
    }
}
