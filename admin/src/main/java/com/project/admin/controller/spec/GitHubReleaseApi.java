package com.project.admin.controller.spec;

import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseUpdateStatusRequest;
import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseListRequest;
import com.project.admin.controller.dto.response.PageResponse;
import com.project.admin.controller.dto.response.release.GitHubReleaseResponse;
import com.project.admin.controller.dto.response.release.GitHubReleaseListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "github_release")
public interface GitHubReleaseApi {

    @Operation(
            summary = "깃허브 릴리즈 목록",
            parameters = {
                    @Parameter(name = "status", description = "상태 (PENDING,PUBLISHED,UNPUBLISHED,DELETED", example = "PENDING"),
                    @Parameter(name = "page", description = "페이지 번호 1부터 시작", example = "1"),
                    @Parameter(name = "size", description = "페이지 크기 최대 20", example = "20")
            }
    )
    ResponseEntity<PageResponse<GitHubReleaseListResponse>> getGitHubReleases(
            @Valid GitHubReleaseListRequest request);

    @Operation(
            summary = "릴리즈 상세 조회",
            parameters = {
                    @Parameter(name = "techStack", description = "기술 스택 (REACT,SPRING_BOOT 등,", example = "REACT"),
                    @Parameter(name = "tagName", description = "릴리즈 태그명", example = "v0.0.1")
            })
    ResponseEntity<GitHubReleaseResponse> getGitHubRelease(
            @PathVariable String techStack,
            @PathVariable String tagName);


    @Operation(
            summary = "깃허브 릴리즈 상태 변환"
    )
    ResponseEntity<Void> updateStatus(@RequestBody GitHubReleaseUpdateStatusRequest request);
}
