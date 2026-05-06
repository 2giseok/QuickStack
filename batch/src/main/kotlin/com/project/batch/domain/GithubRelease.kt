package com.project.batch.domain

import com.project.batch.constants.Status
import com.project.batch.constants.TechStack
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("github_release")
data class GithubRelease(
    val id: Long = 0,
    val techStack: TechStack,
    val tagName: String,
    val name: String?,
    val body: String?,
    val publishedAt: Instant,
    val prerelease: Boolean,
    val draft: Boolean,
    val status: Status = Status.PENDING,
)