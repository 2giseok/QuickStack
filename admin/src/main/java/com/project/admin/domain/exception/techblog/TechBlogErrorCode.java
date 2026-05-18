package com.project.admin.domain.exception.techblog;

import com.project.admin.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TechBlogErrorCode implements BaseErrorCode {

    TECH_BLOG_ERROR_CODE(HttpStatus.NOT_FOUND, "기술 블로그 정보를 찾을 수 없습니다");

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


