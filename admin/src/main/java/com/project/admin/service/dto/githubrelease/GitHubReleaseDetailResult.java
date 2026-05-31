package com.project.admin.service.dto.githubrelease;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.Status;
import com.project.admin.constant.TechStack;
import com.project.admin.domain.entity.GithubRelease;
import java.time.Instant;
import java.time.LocalDateTime;

public record GitHubReleaseDetailResult(
        String id,
        String techStack,
        String tagName,
        String name,
        String category,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        Boolean prerelease,
        String publisher,
        Status status,
        String body,
        String url) {

    public static GitHubReleaseDetailResult from(GithubRelease release, String publisher) {

        String category = release.getTechStack().getCategory().name();

        return new GitHubReleaseDetailResult(
               String.valueOf( release.getId()),
                release.getTechStack().name(),
                release.getTagName(),
                release.getName(),
                category,
                release.getPublishedAt(),
                release.getPrerelease(),
                publisher,
                release.getStatus(),
                release.getBody(),
                release.getReleaseUrl()
        );
    }


}

