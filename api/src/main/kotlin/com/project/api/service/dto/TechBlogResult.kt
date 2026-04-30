package com.project.api.service.dto

import com.project.api.constants.Region
import com.project.api.entity.TechBlog
import java.time.LocalDateTime

data class TechBlogResults(
    val techBlogs: List<TechBlogResult>,
    val pagination: PageResult,
)

data class TechBlogResult(
    val id: Long,
    val region: Region,
    val title: String,
    val url: String,
    val publishedAt: LocalDateTime,
) {
    companion object {
        fun from(techBlog: TechBlog) = TechBlogResult(
            id = techBlog.id,
            region = techBlog.region,
            title = techBlog.title,
            url = techBlog.url,
            publishedAt = techBlog.publishedAt,
        )
    }
}
