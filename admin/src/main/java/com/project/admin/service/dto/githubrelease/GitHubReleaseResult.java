package com.project.admin.service.dto.githubrelease;

import com.project.admin.domain.entity.GithubRelease;
import java.time.LocalDateTime;

public record GitHubReleaseResult(
        String id,
        String techStack,
        String tagName,
        String name,
        String category,
        LocalDateTime publishedAt,
        Boolean prerelease) {

    public static GitHubReleaseResult from(GithubRelease release) {

       String category = release.getTechStack().getCategory().name();

        return new GitHubReleaseResult(
               String.valueOf( release.getId()),
                release.getTechStack().name(),
                release.getTagName(),
                release.getName(),
                category,
                release.getPublishedAt(),
                release.getPrerelease()
        );
    }
}
