package com.project.admin.controller.dto.response.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.service.dto.githubrelease.GitHubReleaseResult;
import java.time.LocalDateTime;

public record GitHubReleaseListResponse(
        String id,
        String techStack,
        String tagName,
        String name,
        String category,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        Boolean prerelease,
        String url,
        String releaseUrl) {

    public static GitHubReleaseListResponse of(GitHubReleaseResult result) {
        return new GitHubReleaseListResponse(
                result.id(),
                result.techStack(),
                result.tagName(),
                result.name(),
                result.category(),
                result.publishedAt(),
                result.prerelease(),
                result.url(),
                result.releaseUrl()
        );
    }
}
