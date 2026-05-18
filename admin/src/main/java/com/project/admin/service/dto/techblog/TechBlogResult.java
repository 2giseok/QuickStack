package com.project.admin.service.dto.techblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.BlogRegion;
import com.project.admin.constant.BlogSource;
import com.project.admin.domain.entity.TechBlog;
import java.time.LocalDateTime;

public record TechBlogResult(
        Long id,
        BlogSource source,
        BlogRegion region,
        String title,
        String url,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt) {

    public static TechBlogResult from(TechBlog techBlog) {
        return new TechBlogResult(
                techBlog.getId(),
                techBlog.getSource(),
                techBlog.getRegion(),
                techBlog.getTitle(),
                techBlog.getUrl(),
                techBlog.getPublishedAt()
        );
    }
}