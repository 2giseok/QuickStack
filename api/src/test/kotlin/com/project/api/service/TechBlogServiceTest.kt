package com.project.api.service

import com.project.api.constants.Region
import com.project.api.constants.Source
import com.project.api.constants.Status
import com.project.api.entity.TechBlog
import com.project.api.repository.TechBlogRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

class TechBlogServiceTest {

    private val repository = mockk<TechBlogRepository>()
    private val service = TechBlogService(repository)

    private fun techBlog(id: Long = 1L, title: String = "Spring 관련 글") = TechBlog(
        id = id,
        source = Source.KAKAO_TECH,
        region = Region.DOMESTIC,
        title = title,
        url = "https://example.com/$id",
        publishedAt = LocalDateTime.now(),
        status = Status.PUBLISHED,
    )

    private fun <T : Any> pageOf(content: List<T>, pageable: Pageable = PageRequest.of(0, 20)) =
        PageImpl(content, pageable, content.size.toLong())

    private fun emptyPage(pageable: Pageable = PageRequest.of(0, 20)) =
        PageImpl<TechBlog>(emptyList(), pageable, 0)

    @Test
    fun `검색어로 조회하면 검색어가 포함된 글 목록이 반환된다`() {
        // given
        val keywordSlot = slot<String>()
        every { repository.findByKeyword(capture(keywordSlot), any()) } returns pageOf(listOf(techBlog()))

        // when
        val result = service.getTechBlogs("spring", 1, 20)

        // then
        assertThat(keywordSlot.captured).isEqualTo("spring")
        assertThat(result.techBlogs).hasSize(1)
    }

    @Test
    fun `빈 검색어는 전체 조회로 처리된다`() {
        // given
        every { repository.findByKeyword(null, any()) } returns emptyPage()

        // when
        service.getTechBlogs("", 1, 20)

        // then
        verify { repository.findByKeyword(null, any()) }
    }

    @Test
    fun `공백만 있는 검색어는 전체 조회로 처리된다`() {
        // given
        every { repository.findByKeyword(null, any()) } returns emptyPage()

        // when
        service.getTechBlogs("   ", 1, 20)

        // then
        verify { repository.findByKeyword(null, any()) }
    }

    @Test
    fun `검색어 없이 조회하면 전체 글 목록이 반환된다`() {
        // given
        every { repository.findByKeyword(null, any()) } returns emptyPage()

        // when
        service.getTechBlogs(null, 1, 20)

        // then
        verify { repository.findByKeyword(null, any()) }
    }

    @Test
    fun `페이지 번호는 1부터 시작한다`() {
        // given
        val pageableSlot = slot<Pageable>()
        every { repository.findByKeyword(any(), capture(pageableSlot)) } returns
            PageImpl<TechBlog>(emptyList(), PageRequest.of(1, 10), 0)

        // when
        service.getTechBlogs(null, 2, 10)

        // then
        assertThat(pageableSlot.captured.pageNumber).isEqualTo(1)
        assertThat(pageableSlot.captured.pageSize).isEqualTo(10)
    }

    @Test
    fun `검색 결과가 없으면 빈 목록과 0건의 페이지 정보가 반환된다`() {
        // given
        every { repository.findByKeyword(any(), any()) } returns emptyPage()

        // when
        val result = service.getTechBlogs("nonexistent", 1, 20)

        // then
        assertThat(result.techBlogs).isEmpty()
        assertThat(result.pagination.totalCount).isEqualTo(0)
        assertThat(result.pagination.currentPage).isEqualTo(1)
    }

    @Test
    fun `회사 목록 조회 시 서비스에 등록된 전체 회사가 반환된다`() {
        // when
        val result = service.getCompanies()

        // then
        assertThat(result.companies).hasSize(Source.entries.size)
        assertThat(result.companies.map { it.name })
            .containsExactlyInAnyOrderElementsOf(Source.entries.map { it.name })
    }
}