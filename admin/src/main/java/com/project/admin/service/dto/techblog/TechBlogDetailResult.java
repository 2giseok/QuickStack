package com.project.admin.service.dto.techblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.BlogRegion;
import com.project.admin.constant.Source;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import java.time.LocalDateTime;

public record TechBlogDetailResult(
        Long id,
        Source source,
        BlogRegion blogRegion,
        String title,
        String url,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        String publisher,
        String summaries) {

    public static TechBlogDetailResult from(TechBlog techBlog, TechBlogApproval approval) {

        return new TechBlogDetailResult(
                techBlog.getId(),
                techBlog.getSource(),
                techBlog.getBlogRegion(),
                techBlog.getTitle(),
                techBlog.getUrl(),
                techBlog.getPublishedAt(),
                approval != null ? String.valueOf(approval.getUpdatedBy()) : null,
                approval != null ? approval.getSummary() : null
        );
    }

    ;
}
