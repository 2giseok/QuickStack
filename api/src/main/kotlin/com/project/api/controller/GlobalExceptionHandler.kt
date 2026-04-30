package com.project.api.controller

import com.project.api.exception.ErrorResponse
import com.project.api.exception.CommonErrorCode
import com.project.api.exception.QuickStackException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    @ExceptionHandler(QuickStackException::class)
    fun handleBusinessException(e: QuickStackException): ResponseEntity<ErrorResponse> {
        log.warn("BusinessException: {}", e.print())
        return ResponseEntity
            .status(e.errorCode.httpStatus)
            .body(ErrorResponse.from(e.errorCode))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        e.bindingResult.fieldErrors.forEach {
            log.warn(
                "Validation failed - field: {}, value: {}, message: {}",
                it.field,
                it.rejectedValue,
                it.defaultMessage
            )
        }
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse.from(CommonErrorCode.INVALID_REQUEST))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error("Unhandled exception", e)
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse.from(CommonErrorCode.INTERNAL_SERVER_ERROR))
    }
}