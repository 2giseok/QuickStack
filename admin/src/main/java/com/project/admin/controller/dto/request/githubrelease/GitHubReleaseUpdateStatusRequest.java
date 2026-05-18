package com.project.admin.controller.dto.request.githubrelease;

import com.project.admin.constant.Status;
import java.util.List;

public record GitHubReleaseUpdateStatusRequest(
        List<ReleaseId> ids,
        Status status
) {
    public record ReleaseId(String techStack, String tagName) {}
}
