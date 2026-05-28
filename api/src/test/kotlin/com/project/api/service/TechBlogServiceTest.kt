package com.project.api.service

import com.project.api.constants.Region
import com.project.api.constants.Source
import com.project.api.constants.Status
import com.project.api.entity.TechBlog
import com.project.api.entity.company.CompanyMappingResult
import com.project.api.entity.company.PopularCompanyMappingResult
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
        source = Source.KAKAO,
        region = Region.DOMESTIC,
        title = title,
        url = "https://example.com/$id",
        publishedAt = LocalDateTime.now(),
        status = Status.PUBLISHED,
        tags = null,
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
        // given
        every { repository.findCompanies() } returns emptyList()

        // when
        val result = service.getCompanies()

        // then
        assertThat(result.companies).hasSize(Source.entries.size)
        assertThat(result.companies.map { it.name })
            .containsExactlyInAnyOrderElementsOf(Source.entries.map { it.name })
    }

    @Test
    fun `회사 목록 조회 시 각 회사의 총 게시글 수와 최근 포스팅일이 포함된다`() {
        // given
        val lastPostedAt = LocalDateTime.of(2026, 5, 1, 12, 0)
        every { repository.findCompanies() } returns listOf(
            CompanyMappingResult(Source.TOSS, totalCount = 122L, lastPostedAt = lastPostedAt),
        )

        // when
        val result = service.getCompanies()

        // then
        val toss = result.companies.first { it.name == Source.TOSS.name }
        assertThat(toss.totalPostCount).isEqualTo(122L)
        assertThat(toss.lastPostedAt).isEqualTo(lastPostedAt)
    }

    @Test
    fun `게시글이 없는 회사는 totalPostCount가 0이고 lastPostedAt이 null이다`() {
        // given
        every { repository.findCompanies() } returns emptyList()

        // when
        val result = service.getCompanies()

        // then
        val company = result.companies.first { it.name == Source.KAKAO.name }
        assertThat(company.totalPostCount).isEqualTo(0L)
        assertThat(company.lastPostedAt).isNull()
    }

    @Test
    fun `region 필터로 조회하면 해당 지역의 회사만 반환된다`() {
        // given
        every { repository.findCompanies() } returns emptyList()

        // when
        val result = service.getCompanies(region = Region.DOMESTIC)

        // then
        assertThat(result.companies).isNotEmpty
        assertThat(result.companies.map { it.region }).containsOnly(Region.DOMESTIC)
    }

    @Test
    fun `region 필터 없이 조회하면 국내외 전체 회사가 반환된다`() {
        // given
        every { repository.findCompanies() } returns emptyList()

        // when
        val result = service.getCompanies(region = null)

        // then
        val regions = result.companies.map { it.region }.toSet()
        assertThat(regions).containsExactlyInAnyOrder(Region.DOMESTIC, Region.INTERNATIONAL)
    }

    @Test
    fun `인기 블로그 조회 시 totalPostCount 내림차순으로 정렬된다`() {
        // given
        every { repository.findPopularCompanies(any()) } returns listOf(
            PopularCompanyMappingResult(Source.NAVER_D2, totalCount = 124L, lastPostedAt = null),
            PopularCompanyMappingResult(Source.TOSS, totalCount = 122L, lastPostedAt = null),
            PopularCompanyMappingResult(Source.WOOWAHAN, totalCount = 72L, lastPostedAt = null),
        )

        // when
        val result = service.getPopularCompanies()

        // then
        assertThat(result.companies.map { it.name })
            .containsExactly(Source.NAVER_D2.name, Source.TOSS.name, Source.WOOWAHAN.name)
    }

    @Test
    fun `인기 블로그 조회 시 게시글이 없는 회사는 포함되지 않는다`() {
        // given
        every { repository.findPopularCompanies(any()) } returns listOf(
            PopularCompanyMappingResult(Source.TOSS, totalCount = 122L, lastPostedAt = null),
        )

        // when
        val result = service.getPopularCompanies()

        // then
        assertThat(result.companies.map { it.name }).containsOnly(Source.TOSS.name)
        assertThat(result.companies).hasSize(1)
    }

    @Test
    fun `DOMESTIC 필터 조회 시 국내 회사만 반환되고 게시글 수와 최근 포스팅일과 블로그 URL이 포함된다`() {
        // given
        val lastPostedAt = LocalDateTime.of(2026, 5, 1, 12, 0)
        every { repository.findCompanies() } returns listOf(
            CompanyMappingResult(Source.KAKAO, totalCount = 50L, lastPostedAt = lastPostedAt),
            CompanyMappingResult(Source.NETFLIX, totalCount = 30L, lastPostedAt = lastPostedAt),
        )

        // when
        val result = service.getCompanies(region = Region.DOMESTIC)

        // then
        assertThat(result.companies.map { it.region }).containsOnly(Region.DOMESTIC)
        val kakao = result.companies.first { it.name == Source.KAKAO.name }
        assertThat(kakao.totalPostCount).isEqualTo(50L)
        assertThat(kakao.lastPostedAt).isEqualTo(lastPostedAt)
        assertThat(kakao.url).isEqualTo(Source.KAKAO.url)
    }

    @Test
    fun `INTERNATIONAL 필터 조회 시 해외 회사만 반환되고 게시글 수와 최근 포스팅일과 블로그 URL이 포함된다`() {
        // given
        val lastPostedAt = LocalDateTime.of(2026, 4, 20, 9, 0)
        every { repository.findCompanies() } returns listOf(
            CompanyMappingResult(Source.NETFLIX, totalCount = 88L, lastPostedAt = lastPostedAt),
            CompanyMappingResult(Source.KAKAO, totalCount = 50L, lastPostedAt = lastPostedAt),
        )

        // when
        val result = service.getCompanies(region = Region.INTERNATIONAL)

        // then
        assertThat(result.companies.map { it.region }).containsOnly(Region.INTERNATIONAL)
        val netflix = result.companies.first { it.name == Source.NETFLIX.name }
        assertThat(netflix.totalPostCount).isEqualTo(88L)
        assertThat(netflix.lastPostedAt).isEqualTo(lastPostedAt)
        assertThat(netflix.url).isEqualTo(Source.NETFLIX.url)
    }

    @Test
    fun `DOMESTIC 필터 조회 시 게시글 없는 국내 회사는 totalPostCount가 0이고 lastPostedAt이 null이다`() {
        // given
        every { repository.findCompanies() } returns emptyList()

        // when
        val result = service.getCompanies(region = Region.DOMESTIC)

        // then
        assertThat(result.companies.map { it.region }).containsOnly(Region.DOMESTIC)
        assertThat(result.companies).allSatisfy { company ->
            assertThat(company.totalPostCount).isEqualTo(0L)
            assertThat(company.lastPostedAt).isNull()
            assertThat(company.url).isNotBlank()
        }
    }

    @Test
    fun `INTERNATIONAL 필터 조회 시 게시글 없는 해외 회사는 totalPostCount가 0이고 lastPostedAt이 null이다`() {
        // given
        every { repository.findCompanies() } returns emptyList()

        // when
        val result = service.getCompanies(region = Region.INTERNATIONAL)

        // then
        assertThat(result.companies.map { it.region }).containsOnly(Region.INTERNATIONAL)
        assertThat(result.companies).allSatisfy { company ->
            assertThat(company.totalPostCount).isEqualTo(0L)
            assertThat(company.lastPostedAt).isNull()
            assertThat(company.url).isNotBlank()
        }
    }
}