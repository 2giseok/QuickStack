package com.project.admin.domain.exception.release;

import com.project.admin.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
@Getter
@RequiredArgsConstructor
public enum ReleaseErrorCode implements BaseErrorCode {



    RELEASE_NOT_FOUND(HttpStatus.NOT_FOUND, "릴리즈를 정보를 찾을 수 없습니다");

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


