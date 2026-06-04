package com.project.api.controller

import com.project.api.constants.Category
import com.project.api.controller.dto.CategoryResponseList
import com.project.api.controller.dto.ReleaseNoteDetailResponse
import com.project.api.controller.dto.ReleaseNoteListResponse
import com.project.api.controller.dto.ReleaseNoteResponse
import com.project.api.controller.dto.ReleaseNoteSearchCondition
import com.project.api.controller.spec.ReleaseNoteApiSpecification
import com.project.api.service.ReleaseNoteService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/release-notes")
class ReleaseNoteController(
    private val releaseNoteService: ReleaseNoteService,
) : ReleaseNoteApiSpecification {

    @GetMapping("/categories")
    override fun getCategories(): CategoryResponseList =
        CategoryResponseList.from(Category.entries)

    @GetMapping
    override fun getReleaseNotes(
        @Valid searchCondition: ReleaseNoteSearchCondition
    ): ReleaseNoteListResponse =
        ReleaseNoteListResponse.from(
            releaseNoteService.getReleaseNotes(
                searchCondition.techStack,
                searchCondition.category,
                searchCondition.keyword,
                searchCondition.page,
                searchCondition.size
            )
        )

    @GetMapping("/{id}")
    override fun getReleaseNote(@PathVariable("id") id: String): ReleaseNoteDetailResponse {
        val longId = id.toLong()
        return ReleaseNoteDetailResponse.from(releaseNoteService.getReleaseNote(longId))
    }
}