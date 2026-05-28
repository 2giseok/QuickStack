package com.project.api.controller

import com.project.api.constants.Region
import com.project.api.controller.dto.CompanyListResponse
import com.project.api.controller.dto.PopularCompanyListResponse
import com.project.api.controller.dto.TechBlogListResponse
import com.project.api.controller.dto.TechBlogSearchCondition
import com.project.api.controller.dto.TechBlogSourceSearchCondition
import com.project.api.controller.spec.TechBlogApiSpecification
import com.project.api.service.TechBlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tech-blogs")
class TechBlogController(
    private val techBlogService: TechBlogService,
) : TechBlogApiSpecification {

    @GetMapping("/companies")
    override fun getCompanies(
        @RequestParam(required = false) region: Region?,
    ): CompanyListResponse {
        val results = techBlogService.getCompanies(region)
        return CompanyListResponse.from(results = results)
    }

    @GetMapping("/companies/popular")
    override fun getPopularCompanies(): PopularCompanyListResponse {
        val results = techBlogService.getPopularCompanies()
        return PopularCompanyListResponse.from(results = results)
    }

    @GetMapping
    override fun getTechBlogs(
        @Valid searchCondition: TechBlogSearchCondition,
    ): TechBlogListResponse {
        val results = techBlogService.getTechBlogs(searchCondition.keyword, searchCondition.page, searchCondition.size)
        return TechBlogListResponse.from(results = results)
    }

    @GetMapping("/sources")
    override fun getTechBlogsBySource(
        @Valid searchCondition: TechBlogSourceSearchCondition,
    ): TechBlogListResponse {
        val results = techBlogService.getTechBlogsBySource(searchCondition.source, searchCondition.page, searchCondition.size)
        return TechBlogListResponse.from(results = results)
    }
}
