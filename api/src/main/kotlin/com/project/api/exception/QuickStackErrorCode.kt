package com.project.api.exception

import org.springframework.http.HttpStatus

interface QuickStackErrorCode {

    val name: String

    val code: String

    val message: String

    val httpStatus: HttpStatus
}
