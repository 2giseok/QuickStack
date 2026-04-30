package com.project.api.service

import com.project.api.constants.Category
import com.project.api.constants.Status
import com.project.api.constants.TechStack
import com.project.api.entity.GithubRelease
import com.project.api.entity.GithubReleaseId
import com.project.api.repository.GithubReleaseRepository
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

class ReleaseNoteServiceTest {

    private val repository = mockk<GithubReleaseRepository>()
    private val service = ReleaseNoteService(repository)

    private fun release(techStack: TechStack, tagName: String = "v1.0.0") = GithubRelease(
        id = GithubReleaseId(techStack = techStack, tagName = tagName),
        name = "${techStack.en} $tagName",
        body = null,
        publishedAt = LocalDateTime.now(),
        prerelease = false,
        draft = false,
        status = Status.PUBLISHED,
    )

    private fun emptyPage(pageable: Pageable = PageRequest.of(0, 20)) =
        PageImpl<GithubRelease>(emptyList(), pageable, 0)

    @Test
    fun `카테고리와 검색어 없이 조회하면 전체 릴리즈 노트가 반환된다`() {
        // given
        val releases = listOf(release(TechStack.SPRING_BOOT), release(TechStack.REACT))
        every { repository.findByTechStacks(any(), any()) } returns PageImpl(releases, PageRequest.of(0, 20), 2)

        // when
        val result = service.getReleaseNotes(Category.ALL, null, 1, 20)

        // then
        assertThat(result.releaseNotes).hasSize(2)
        assertThat(result.pagination.totalCount).isEqualTo(2)
        assertThat(result.pagination.currentPage).isEqualTo(1)
    }

    @Test
    fun `공백 검색어는 검색어가 없는 것과 동일하게 전체 조회한다`() {
        // given
        every { repository.findByTechStacks(any(), any()) } returns emptyPage()

        // when
        service.getReleaseNotes(Category.ALL, "   ", 1, 20)

        // then
        verify(exactly = 1) { repository.findByTechStacks(any(), any()) }
    }

    @Test
    fun `매칭되는 기술 스택이 없는 검색어는 결과가 없다`() {
        // given
        val keyword = "존재하지않는기술스택xyzxyz"

        // when
        val result = service.getReleaseNotes(Category.ALL, keyword, 1, 20)

        // then
        assertThat(result.releaseNotes).isEmpty()
        assertThat(result.pagination.totalCount).isEqualTo(0)
        verify(exactly = 0) { repository.findByTechStacks(any(), any()) }
    }

    @Test
    fun `검색어가 있으면 카테고리와 관계없이 검색어 기준으로만 조회된다`() {
        // given
        val techStacksSlot = slot<Set<TechStack>>()
        every { repository.findByTechStacks(capture(techStacksSlot), any()) } returns emptyPage()

        // when
        service.getReleaseNotes(Category.DEVOPS, "spring", 1, 20)

        // then
        assertThat(techStacksSlot.captured).allMatch { it.en.contains("Spring", ignoreCase = true) }
        assertThat(techStacksSlot.captured).doesNotContain(TechStack.DOCKER, TechStack.KUBERNETES)
    }

    @Test
    fun `카테고리만 선택하면 해당 카테고리의 기술 스택 전체를 조회한다`() {
        // given
        val techStacksSlot = slot<Set<TechStack>>()
        every { repository.findByTechStacks(capture(techStacksSlot), any()) } returns emptyPage()

        // when
        service.getReleaseNotes(Category.DEVOPS, null, 1, 20)

        // then
        assertThat(techStacksSlot.captured)
            .containsExactlyInAnyOrderElementsOf(Category.DEVOPS.techStacks)
    }

    @Test
    fun `전체 카테고리에서 검색하면 모든 카테고리의 기술 스택이 검색 대상이 된다`() {
        // given
        val techStacksSlot = slot<Set<TechStack>>()
        every { repository.findByTechStacks(capture(techStacksSlot), any()) } returns emptyPage()

        // when
        service.getReleaseNotes(Category.ALL, "docker", 1, 20)

        // then
        assertThat(techStacksSlot.captured).contains(TechStack.DOCKER)
    }

    @Test
    fun `페이지 번호는 1부터 시작한다`() {
        // given
        val pageableSlot = slot<Pageable>()
        every { repository.findByTechStacks(any(), capture(pageableSlot)) } returns
            PageImpl(emptyList(), PageRequest.of(2, 5), 0)

        // when
        service.getReleaseNotes(Category.ALL, null, 3, 5)

        // then
        assertThat(pageableSlot.captured.pageNumber).isEqualTo(2)
        assertThat(pageableSlot.captured.pageSize).isEqualTo(5)
    }

    @Test
    fun `검색 결과가 없으면 빈 목록과 페이지 정보가 함께 반환된다`() {
        // given
        every { repository.findByTechStacks(any(), any()) } returns emptyPage()

        // when
        val result = service.getReleaseNotes(Category.ALL, null, 1, 20)

        // then
        assertThat(result.releaseNotes).isEmpty()
        assertThat(result.pagination.totalCount).isEqualTo(0)
        assertThat(result.pagination.pageSize).isEqualTo(20)
        assertThat(result.pagination.currentPage).isEqualTo(1)
    }
}