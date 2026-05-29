package com.project.admin.service.dto.techblog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.Region;
import com.project.admin.constant.Source;
import com.project.admin.constant.Status;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import java.time.LocalDateTime;
import java.util.List;

public record TechBlogDetailResult(
        Long id,
        Source source,
        Region region,
        String title,
        String url,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        String publisher,
        Status status,
        String summaries,
        List<String> tags) {

    public static TechBlogDetailResult from(TechBlog techBlog, TechBlogApproval approval) {

        return new TechBlogDetailResult(
                techBlog.getId(),
                techBlog.getSource(),
                techBlog.getRegion(),
                techBlog.getTitle(),
                techBlog.getUrl(),
                techBlog.getPublishedAt(),
                approval != null ? String.valueOf(approval.getUpdatedBy()) : null,
                techBlog.getStatus(),
                approval != null ? approval.getSummary() : null,
                techBlog.getTags()

        );
    }

    ;
}
