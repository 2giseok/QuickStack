package com.project.api.exception

data class ErrorResponse(
    val code: String,
    val message: String,
) {
    companion object {
        fun from(errorCode: QuickStackErrorCode) = ErrorResponse(
            code = errorCode.code,
            message = errorCode.message,
        )
    }
}