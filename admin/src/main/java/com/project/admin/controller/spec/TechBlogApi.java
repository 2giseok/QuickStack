package com.project.admin.controller.spec;

import com.project.admin.constant.Status;
import com.project.admin.controller.dto.request.techblog.TechBlogUpdateStatusRequest;
import com.project.admin.controller.dto.response.PageResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogDetailResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "admin_tech_blog")
public interface TechBlogApi {

    @Operation(
            summary = "기술 블로그 목록",
    parameters = {
            @Parameter(name = "status", description = "상태 (PENDING,PUBLISHED,UNPUBLISHED,DELETED", example = "PENDING"),
            @Parameter(name = "page", description = "페이지 번호 1부터 시작", example = "1"),
            @Parameter(name = "size", description = "페이지 크기 최대 20", example = "20")
    })
    ResponseEntity<PageResponse<TechBlogListResponse>> getTechBlogs(Status status, int page, int size);

    @Operation(
            summary = "기술 블로그 상세 조회",
    parameters = {
                    @Parameter(name = "id",description = "기술 블로그 글 ID",example = "1")
    })
    ResponseEntity<TechBlogDetailResponse> getTechBlog(@PathVariable Long id);

    @Operation(
            summary = "기술 블로그 상태 변환",
    parameters = {
                    @Parameter(name = "ids",description = "ID 리스트",example = "[1,2,3]"),
            @Parameter(name = "status",description = "상태",example = "PUBLISHED,DELETE")
    })
    ResponseEntity<Void> updateStatus(@RequestBody TechBlogUpdateStatusRequest request);

}
