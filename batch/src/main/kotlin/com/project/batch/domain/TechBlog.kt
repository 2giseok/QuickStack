package com.project.batch.domain

import com.project.batch.constants.BlogRegion
import com.project.batch.constants.Status
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("tech_blog")
data class TechBlog(
    @Id val id: Long,
    val source: String,
    val region: BlogRegion,
    val title: String,
    val url: String,
    val publishedAt: Instant,
    val status: Status = Status.PENDING,
)