package com.project.api.exception

import org.springframework.http.HttpStatus

enum class TechStackErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
) : QuickStackErrorCode {

    INVALID_CATEGORY("INVALID_CATEGORY", "유효하지 않은 카테고리입니다.", HttpStatus.BAD_REQUEST),
}