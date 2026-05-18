package com.project.admin.exception;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {
    HttpStatus status();

    String message();
}