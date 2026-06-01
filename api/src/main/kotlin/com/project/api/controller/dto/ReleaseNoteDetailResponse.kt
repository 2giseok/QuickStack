package com.project.api.controller.dto

import com.project.api.constants.Category
import com.project.api.constants.TechStack
import com.project.api.service.dto.ReleaseNoteDetailResults
import java.time.LocalDateTime

data class ReleaseNoteDetailResponse(
    val id: String,
    val techStack: TechStack,
    val tagName: String,
    val name: String?,
    val body: String?,
    val publishedAt: LocalDateTime,
    val prerelease: Boolean,
    val category: Category,
    val url: String
) {
    companion object {
        fun from(release: ReleaseNoteDetailResults) = ReleaseNoteDetailResponse(
            id = release.id,
            techStack = release.techStack,
            tagName = release.tagName,
            name = release.name,
            body = release.body,
            publishedAt = release.publishedAt,
            prerelease = release.prerelease,
            category = Category.from(release.techStack),
            url =  release.url
        )
    }
}
