package com.project.api.controller.dto

import com.project.api.constants.TechStack
import com.project.api.service.dto.ReleaseNoteResult
import com.project.api.service.dto.ReleaseNoteResults
import java.time.LocalDateTime

data class ReleaseNoteListResponse(
    val releaseNotes: List<ReleaseNoteResponse>,
    val pagination: PageResponse,
) {
    companion object {
        fun from(results: ReleaseNoteResults) = ReleaseNoteListResponse(
            releaseNotes = results.releaseNotes.map { ReleaseNoteResponse.from(it) },
            pagination = PageResponse.from(results.pagination),
        )
    }
}

data class ReleaseNoteResponse(
    val id: String,
    val techStack: TechStack,
    val tagName: String,
    val name: String?,
    val category: String,
    val publishedAt: LocalDateTime,
) {
    companion object {
        fun from(result: ReleaseNoteResult) = ReleaseNoteResponse(
            id = result.id.toString(),
            techStack = result.techStack,
            tagName = result.tagName,
            name = result.name,
            category = result.category.code,
            publishedAt = result.publishedAt,
        )
    }
}