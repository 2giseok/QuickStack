package com.project.api.controller

import com.project.api.controller.dto.CompanyListResponse
import com.project.api.controller.dto.TechBlogListResponse
import com.project.api.controller.dto.TechBlogSearchCondition
import com.project.api.controller.spec.TechBlogApiSpecification
import com.project.api.service.TechBlogService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tech-blogs")
class TechBlogController(
    private val techBlogService: TechBlogService,
) : TechBlogApiSpecification {

    @GetMapping("/companies")
    override fun getCompanies(): CompanyListResponse =
        CompanyListResponse.from(techBlogService.getCompanies())

    @GetMapping
    override fun getTechBlogs(
        @Valid searchCondition: TechBlogSearchCondition,
    ): TechBlogListResponse =
        TechBlogListResponse.from(techBlogService.getTechBlogs(searchCondition.keyword, searchCondition.page, searchCondition.size))
}