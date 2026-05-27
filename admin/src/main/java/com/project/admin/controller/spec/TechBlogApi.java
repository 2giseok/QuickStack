package com.project.admin.controller.spec;

import com.project.admin.controller.dto.request.techblog.TechBlogListRequest;
import com.project.admin.controller.dto.request.techblog.TechBlogUpdateStatusRequest;
import com.project.admin.controller.dto.response.PageResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogDetailResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "admin_tech_blog")
public interface TechBlogApi {

    @Operation(
            summary = "기술 블로그 목록",
    parameters = {
            @Parameter(name = "status", description = "상태 (PENDING,PUBLISHED,UNPUBLISHED)", example = "PENDING"),
            @Parameter(name = "page", description = "페이지 번호 1부터 시작", example = "1"),
            @Parameter(name = "size", description = "페이지 크기 최대 20", example = "20"),
            @Parameter(name = "source", description = "블로그 출처 (WOOWAHAN, KAKAO_TECH 등)", example = "WOOWAHAN")
    })
    ResponseEntity<PageResponse<TechBlogListResponse>> getTechBlogs(@Valid TechBlogListRequest request);

    @Operation(
            summary = "기술 블로그 상세 조회",
    parameters = {
                    @Parameter(name = "id",description = "기술 블로그 글 ID",example = "317948546257846273")
    })
    ResponseEntity<TechBlogDetailResponse> getTechBlog(@PathVariable String id);

    @Operation(
            summary = "기술 블로그 상태 변환",
    parameters = {
                    @Parameter(name = "ids",description = "ID 리스트",example = "[1,2,3]"),
            @Parameter(name = "status",description = "변경할 상태 (PUBLISHED, UNPUBLISHED",example = "DELETE")
    })
    ResponseEntity<Void> updateStatus(@RequestBody TechBlogUpdateStatusRequest request);

}
