package com.project.api.entity

import com.project.api.constants.Region
import com.project.api.constants.Source
import com.project.api.constants.Status
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tech_blog")
class TechBlog(

    @Id
    val id: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    val source: Source,

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    val region: Region,

    @Column(name = "title")
    val title: String,

    @Column(name = "url", length = 1000)
    val url: String,

    @Column(name = "published_at")
    val publishedAt: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: Status,
)
