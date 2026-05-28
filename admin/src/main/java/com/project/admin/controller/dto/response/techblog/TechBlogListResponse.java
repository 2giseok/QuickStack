package com.project.admin.controller.dto.response.techblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.Region;
import com.project.admin.constant.Source;
import com.project.admin.service.dto.techblog.TechBlogResult;
import java.time.LocalDateTime;

public record TechBlogListResponse(
        String id,
        Source source,
        Region region,
        String title,
        String url,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt) {

    public static TechBlogListResponse of(TechBlogResult techBlog) {
        return new TechBlogListResponse(
                String.valueOf(techBlog.id()),
                techBlog.source(),
                techBlog.region(),
                techBlog.title(),
                techBlog.url(),
                techBlog.publishedAt()
        );
    }
}
