package com.project.crawler.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    PENDING("PENDING", "검수 대기"),
    PUBLISHED("PUBLISHED", "발행"),
    UNPUBLISHED("UNPUBLISHED", "미발행"),
    DELETED("DELETED", "삭제"),
    FAILED("FAILED","403 오류");

    private final String code;
    private final String message;


}