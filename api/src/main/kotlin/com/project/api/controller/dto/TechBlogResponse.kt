package com.project.api.controller.dto

import com.project.api.constants.Region
import com.project.api.service.dto.TechBlogResult
import com.project.api.service.dto.TechBlogResults
import java.time.LocalDateTime

data class TechBlogListResponse(
    val techBlogs: List<TechBlogResponse>,
    val pagination: PageResponse,
) {
    companion object {
        fun from(results: TechBlogResults) = TechBlogListResponse(
            techBlogs = results.techBlogs.map { TechBlogResponse.from(it) },
            pagination = PageResponse.from(results.pagination),
        )
    }
}

data class TechBlogResponse(
    val id: Long,
    val region: Region,
    val title: String,
    val url: String,
    val publishedAt: LocalDateTime,
) {
    companion object {
        fun from(result: TechBlogResult) = TechBlogResponse(
            id = result.id,
            region = result.region,
            title = result.title,
            url = result.url,
            publishedAt = result.publishedAt,
        )
    }
}