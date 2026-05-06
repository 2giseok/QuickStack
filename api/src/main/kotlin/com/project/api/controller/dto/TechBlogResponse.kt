package com.project.api.controller.dto

import com.project.api.constants.Region
import com.project.api.constants.Source
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
    val source: Source,
    val ko: String,
    val en: String,
    val region: Region,
    val title: String,
    val publishedAt: LocalDateTime,
    val tags: List<String>,
) {
    companion object {
        fun from(result: TechBlogResult) = TechBlogResponse(
            id = result.id,
            source = result.source,
            ko = result.source.ko,
            en = result.source.en,
            region = result.region,
            title = result.title,
            publishedAt = result.publishedAt,
            tags = result.tags,
        )
    }
}