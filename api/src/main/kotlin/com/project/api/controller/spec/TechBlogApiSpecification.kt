package com.project.api.controller.spec

import com.project.api.constants.Region
import com.project.api.controller.dto.CompanyListResponse
import com.project.api.controller.dto.PopularCompanyListResponse
import com.project.api.controller.dto.TechBlogListResponse
import com.project.api.controller.dto.TechBlogSearchCondition
import com.project.api.controller.dto.TechBlogSourceSearchCondition
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid

@Tag(name = "Tech Blog", description = "기술 블로그 API")
interface TechBlogApiSpecification {

    @Operation(
        summary = "기술 블로그 기업 목록 조회",
        parameters = [
            Parameter(name = "region", description = "지역 필터 (DOMESTIC: 국내, INTERNATIONAL: 국외). 미입력 시 전체 반환", example = "DOMESTIC"),
        ]
    )
    fun getCompanies(region: Region?): CompanyListResponse

    @Operation(summary = "인기 블로그 API")
    fun getPopularCompanies(): PopularCompanyListResponse

    @Operation(
        summary = "기술 블로그 목록 조회",
        parameters = [
            Parameter(name = "keyword", description = "기업명 또는 본문 검색어", example = "kakao"),
            Parameter(name = "page", description = "페이지 번호 (1부터 시작)", example = "1"),
            Parameter(name = "size", description = "페이지 크기 (최대 20)", example = "20"),
        ]
    )
    fun getTechBlogs(
        @Valid searchCondition: TechBlogSearchCondition,
    ): TechBlogListResponse

    @Operation(
        summary = "블로그명 검색 API",
        parameters = [
            Parameter(name = "source", description = "소스 코드", example = "DAANGN"),
            Parameter(name = "page", description = "페이지 번호 (1부터 시작)", example = "1"),
            Parameter(name = "size", description = "페이지 크기 (최대 20)", example = "20"),
        ]
    )
    fun getTechBlogsBySource(
        @Valid searchCondition: TechBlogSourceSearchCondition,
    ): TechBlogListResponse
}
