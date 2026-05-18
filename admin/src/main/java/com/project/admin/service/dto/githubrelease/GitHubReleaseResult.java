package com.project.admin.service.dto.githubrelease;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.admin.constant.TechStack;
import com.project.admin.domain.entity.GithubRelease;
import java.time.Instant;
import java.time.LocalDateTime;

public record GitHubReleaseResult(
        String techStack,
        String tagName,
        String name,
        String category,
        LocalDateTime publishedAt,
        Boolean prerelease) {

    public static GitHubReleaseResult from(GithubRelease release) {

        TechStack techStack = TechStack.valueOf(
                release.getGithubReleaseId().getTechStack()
        );
        return new GitHubReleaseResult(
                release.getGithubReleaseId().getTechStack(),
                release.getGithubReleaseId().getTagName(),
                release.getName(),
                techStack.getCategory().name(),
                release.getPublishedAt(),
                release.getPrerelease()
        );
    }
}
