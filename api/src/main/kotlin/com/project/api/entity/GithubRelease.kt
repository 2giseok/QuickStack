package com.project.api.entity

import com.project.api.constants.Status
import com.project.api.constants.TechStack
import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "github_release")
class GithubRelease(

    @EmbeddedId
    val id: GithubReleaseId,

    @Column(name = "name", length = 500)
    var name: String?,

    @Column(name = "body", columnDefinition = "mediumtext")
    var body: String?,

    @Column(name = "published_at", nullable = false, columnDefinition = "datetime(6)")
    val publishedAt: LocalDateTime,

    @Column(name = "prerelease", nullable = false)
    val prerelease: Boolean,

    @Column(name = "draft", nullable = false)
    val draft: Boolean,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    var status: Status
) {
    val techStack: TechStack get() = id.techStack
    val tagName: String get() = id.tagName
}

@Embeddable
data class GithubReleaseId(

    @Enumerated(EnumType.STRING)
    @Column(name = "tech_stack", length = 100, nullable = false)
    val techStack: TechStack,

    @Column(name = "tag_name", length = 100, nullable = false)
    val tagName: String
) : Serializable