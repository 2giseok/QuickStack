package com.project.api.service.dto

import com.project.api.constants.TechStack
import com.project.api.constants.Category
import com.project.api.entity.GithubRelease
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

data class ReleaseNoteResults(
    val releaseNotes: List<ReleaseNoteResult>,
    val pagination: PageResult,
) {
    companion object {
        fun of(releaseNotes: List<ReleaseNoteResult>, pagination: PageResult) = ReleaseNoteResults(
            releaseNotes = releaseNotes,
            pagination = pagination,
        )

        fun empty(pageable: Pageable) = ReleaseNoteResults(
            releaseNotes = emptyList(),
            pagination = PageResult(
                totalCount = 0,
                pageSize = pageable.pageSize,
                currentPage = pageable.pageNumber + 1,
            ),
        )
    }
}

data class ReleaseNoteResult(
    val techStack: TechStack,
    val tagName: String,
    val name: String?,
    val category: Category,
    val publishedAt: LocalDateTime,
    val prerelease: Boolean,
) {
    companion object {
        fun from(release: GithubRelease) = ReleaseNoteResult(
            techStack = release.techStack,
            tagName = release.tagName,
            name = release.name,
            category = Category.from(release.techStack),
            publishedAt = release.publishedAt,
            prerelease = release.prerelease,
        )
    }
}
