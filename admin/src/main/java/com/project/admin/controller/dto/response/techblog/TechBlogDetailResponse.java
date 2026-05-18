package com.project.admin.controller.dto.response.techblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.BlogRegion;
import com.project.admin.constant.BlogSource;
import com.project.admin.service.dto.techblog.TechBlogDetailResult;
import java.time.LocalDateTime;

public record TechBlogDetailResponse(
        Long id,
        BlogSource source,
        BlogRegion region,
        String title,
        String url,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        String publisher,
        String summaries
) {

    public static TechBlogDetailResponse of(TechBlogDetailResult result) {
        return new TechBlogDetailResponse(
                result.id(),
                result.source(),
                result.region(),
                result.title(),
                result.url(),
                result.publishedAt(),
                result.publisher(),
                result.summaries()
        );
    }
}
