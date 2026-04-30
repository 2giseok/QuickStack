package com.project.admin.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Map;

@Getter
public class ErrorResponse {

    private final int status;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> validationErrors;


    private ErrorResponse(int status, String message, Map<String, String> validationErrors) {
        this.status = status;
        this.message = message;
        this.validationErrors = validationErrors;
    }


    public static ErrorResponse from(BaseErrorCode errorCode) {

        return new ErrorResponse(errorCode.status().value(), errorCode.message(), null);
    }

    public static ErrorResponse of(int status, String message, Map<String, String> validationErrors) {
        return new ErrorResponse(status, message, validationErrors);
    }
}
