package com.project.admin.service.dto.githubrelease;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.TechStack;
import com.project.admin.domain.entity.GithubRelease;
import java.time.Instant;
import java.time.LocalDateTime;

public record GitHubReleaseDetailResult(
        String techStack,
        String tagName,
        String name,
        String category,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime publishedAt,
        Boolean prerelease,
        String publisher,
        String body) {

    public static GitHubReleaseDetailResult from(GithubRelease release, String publisher,
            TechStack techStack) {

        return new GitHubReleaseDetailResult(
                release.getGithubReleaseId().getTechStack(),
                release.getGithubReleaseId().getTagName(),
                release.getName(),
                techStack.getCategory().name(),
                release.getPublishedAt(),
                release.getPrerelease(),
                publisher,
                release.getBody());
    }


}

