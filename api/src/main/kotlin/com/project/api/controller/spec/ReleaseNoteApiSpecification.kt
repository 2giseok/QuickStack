package com.project.api.controller.spec

import com.project.api.controller.dto.CategoryResponseList
import com.project.api.controller.dto.ReleaseNoteListResponse
import com.project.api.controller.dto.ReleaseNoteSearchCondition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Tag(name = "Release Note", description = "릴리즈 노트 API")
interface ReleaseNoteApiSpecification {

    @Operation(summary = "릴리즈 노트 카테고리 API")
    fun getCategories(): CategoryResponseList

    @Operation(
        summary = "릴리즈 노트 목록 API",
        parameters = [
            Parameter(name = "category", description = "카테고리 (ALL, FRONTEND, BACKEND, DEVOPS, LANGUAGE, AI)", example = "ALL"),
            Parameter(name = "keyword", description = "기술 스택 검색어", example = "REACT"),
            Parameter(name = "page", description = "페이지 번호 (1부터 시작)", example = "1"),
            Parameter(name = "size", description = "페이지 크기 (최대 20)", example = "20"),
        ]
    )
    fun getReleaseNotes(
        @Valid searchCondition: ReleaseNoteSearchCondition
    ): ReleaseNoteListResponse
}