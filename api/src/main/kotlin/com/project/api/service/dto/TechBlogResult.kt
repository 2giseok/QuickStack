package com.project.api.service.dto

import com.project.api.constants.Region
import com.project.api.constants.Source
import com.project.api.entity.TechBlog
import java.time.LocalDateTime

data class TechBlogResults(
    val techBlogs: List<TechBlogResult>,
    val pagination: PageResult,
) {
    companion object {
        fun empty(page: Int, size: Int) = TechBlogResults(
            techBlogs = emptyList(),
            pagination = PageResult(totalCount = 0, pageSize = size, currentPage = page),
        )
    }
}

data class TechBlogResult(
    val id: Long,
    val source: Source,
    val region: Region,
    val title: String,
    val publishedAt: LocalDateTime,
    val tags: List<String>,
    val url: String,
    val techBlogUrl: String,
) {
    companion object {
        fun from(techBlog: TechBlog) = TechBlogResult(
            id = techBlog.id,
            source = techBlog.source,
            region = techBlog.region,
            title = techBlog.title,
            publishedAt = techBlog.publishedAt,
            tags = techBlog.tags ?: emptyList(),
            url = techBlog.url,
            techBlogUrl = techBlog.source.url
        )
    }
}
