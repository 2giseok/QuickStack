package com.project.admin.controller.dto.response.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.service.dto.githubrelease.GitHubReleaseDetailResult;
import java.time.LocalDateTime;

public record GitHubReleaseResponse(
        String id,
        String techStack,
        String tagName,
        String name,
        String category,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        Boolean prerelease,
        String publisher,
        String body) {


    public static GitHubReleaseResponse of(GitHubReleaseDetailResult result) {

        return new GitHubReleaseResponse(
                result.id(),
                result.techStack(),
                result.tagName(),
                result.name(),
                result.category(),
                result.publishedAt(),
                result.prerelease(),
                result.publisher(),
                result.body()

        );
    }
}




