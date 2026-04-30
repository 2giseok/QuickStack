package com.project.api.exception

import org.springframework.http.HttpStatus

enum class CommonErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: HttpStatus,
) : QuickStackErrorCode {

    INVALID_REQUEST("INVALID_REQUEST", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
}