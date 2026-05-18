package com.project.admin.domain.exception.summary;

import com.project.admin.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SummaryErrorCode implements BaseErrorCode {

    CONTENT_NOT_FOUND(HttpStatus.NOT_FOUND,"크롤링 완료된 content가 없습니다"),
    AI_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "AI 서버 일시적 오류입니다. 다시 시도 해주세요"),
    SUMMARY_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "요약에 실패했습니다");

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
