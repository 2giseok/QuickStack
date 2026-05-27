package com.project.admin.controller;

import com.project.admin.controller.dto.request.techblog.TechBlogListRequest;
import com.project.admin.controller.dto.request.techblog.TechBlogUpdateStatusRequest;
import com.project.admin.controller.dto.response.PageResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogDetailResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogListResponse;
import com.project.admin.controller.spec.TechBlogApi;
import com.project.admin.service.TechBlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/techblog")
public class TechBlogController implements TechBlogApi {

    private final TechBlogService techBlogService;

    @GetMapping
    public ResponseEntity<PageResponse<TechBlogListResponse>> getTechBlogs(
            @Valid TechBlogListRequest request
    ) {
        Page<TechBlogListResponse> results = techBlogService.getTechBlogs(request)
                .map(TechBlogListResponse::of);

        PageResponse<TechBlogListResponse> result = PageResponse.of(results, request.page());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechBlogDetailResponse> getTechBlog(@PathVariable String id) {
        TechBlogDetailResponse response = TechBlogDetailResponse.of(
                techBlogService.getTechBlog(Long.valueOf(id))
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateStatus(@RequestBody TechBlogUpdateStatusRequest request) {

        List<Long> ids = request.ids().stream()
                .map(Long::valueOf)
                .toList();

        techBlogService.updateStatus(ids, request.status());
        return ResponseEntity.ok().build();
    }

}
