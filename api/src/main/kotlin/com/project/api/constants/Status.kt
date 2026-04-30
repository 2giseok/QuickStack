package com.project.api.constants

enum class Status(val code: String, val description: String) {
    PENDING("PENDING", "검수 대기"),
    PUBLISHED("PUBLISHED", "발행"),
    UNPUBLISHED("UNPUBLISHED", "미발행"),
    DELETED("DELETED", "삭제"),
    ;

    companion object {
        fun fromCode(code: String): Status =
            entries.find { it.code == code } ?: throw IllegalArgumentException("Unknown status code: $code")
    }
}