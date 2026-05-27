package com.project.admin.controller;

import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseUpdateStatusRequest;
import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseListRequest;
import com.project.admin.controller.dto.response.PageResponse;
import com.project.admin.controller.dto.response.release.GitHubReleaseResponse;
import com.project.admin.controller.dto.response.release.GitHubReleaseListResponse;
import com.project.admin.controller.spec.GitHubReleaseApi;
import com.project.admin.service.GitHubReleaseService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/release")
@RequiredArgsConstructor
public class GitHubReleaseController implements GitHubReleaseApi {

    private final GitHubReleaseService gitHubReleaseService;

    @GetMapping
    public ResponseEntity<PageResponse<GitHubReleaseListResponse>> getGitHubReleases(
            @Valid GitHubReleaseListRequest request) {
        Page<GitHubReleaseListResponse> responses = gitHubReleaseService.getGitHubReleases(request)
                .map(GitHubReleaseListResponse::of);

        PageResponse<GitHubReleaseListResponse> result = PageResponse.of(responses, request.page());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GitHubReleaseResponse> getGitHubRelease(
            @PathVariable String id
    ) {

        Long longId =  Long.valueOf(id);

        GitHubReleaseResponse response = GitHubReleaseResponse.of(
                gitHubReleaseService.getGitHubRelease(longId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateStatus(
            @RequestBody GitHubReleaseUpdateStatusRequest request
    ) {
        List<Long> ids = request.ids().stream()
                .map(Long::valueOf)
                .toList();

        gitHubReleaseService.updateStatus(ids, request.status());
        return ResponseEntity.ok().build();
    }
}
