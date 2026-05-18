package com.project.admin.controller.dto.response.techblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.BlogRegion;
import com.project.admin.constant.BlogSource;
import com.project.admin.service.dto.techblog.TechBlogResult;
import java.time.LocalDateTime;

public record TechBlogListResponse(
        Long id,
        BlogSource source,
        BlogRegion region,
        String title,
        String url,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt) {

    public static TechBlogListResponse of(TechBlogResult techBlog) {
        return new TechBlogListResponse(
                techBlog.id(),
                techBlog.source(),
                techBlog.region(),
                techBlog.title(),
                techBlog.url(),
                techBlog.publishedAt()
        );
    }
}
