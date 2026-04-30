package com.project.batch.remote.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubReleaseResponse(
    val id: Long,
    @JsonProperty("tag_name") val tagName: String,
    val name: String?,
    val body: String?,
    @JsonProperty("published_at") val publishedAt: String,
    val prerelease: Boolean,
    val draft: Boolean,
)