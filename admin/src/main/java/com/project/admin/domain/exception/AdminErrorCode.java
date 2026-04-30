package com.project.admin.domain.exception;

import com.project.admin.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AdminErrorCode implements BaseErrorCode {


    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 일치하지 않습니다"),
    DUPLICATE_LOGIN_ID(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디 입니다");

    private final HttpStatus status;
    private final String message;


    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}
