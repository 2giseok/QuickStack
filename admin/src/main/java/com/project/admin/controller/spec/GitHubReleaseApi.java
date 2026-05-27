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
                    @Parameter(name = "size", description = "페이지 크기 최대 20", example = "20"),
                    @Parameter(name = "category", description = "카테고리 (FRONTEND, BACKEND, DEVOPS, LANGUAGE, AI)", example = "FRONTEND"),
                    @Parameter(name = "techStack", description = "특정 기술 (REACT, SPRING_BOOT 등)", example = "REACT")

            }
    )
    ResponseEntity<PageResponse<GitHubReleaseListResponse>> getGitHubReleases(
            @Valid GitHubReleaseListRequest request);

    @Operation(
            summary = "릴리즈 상세 조회",
            parameters = {
                    @Parameter(name = "id", description = "릴리즈 ID", example = "1")
            })
    ResponseEntity<GitHubReleaseResponse> getGitHubRelease(
            @PathVariable String id);


    @Operation(
            summary = "깃허브 릴리즈 상태 변환",
            parameters = {
                    @Parameter(name = "ids", description = "ID 리스트", example = "[1,2,3]"),
                    @Parameter(name = "status", description = "변경할 상태 (PUBLISHED, UNPUBLISHED", example = "DELETE")
            }
    )
    ResponseEntity<Void> updateStatus(@RequestBody GitHubReleaseUpdateStatusRequest request);
}
