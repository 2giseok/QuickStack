package com.project.admin.controller;

import com.project.admin.constant.Status;
import com.project.admin.controller.dto.request.techblog.TechBlogUpdateStatusRequest;
import com.project.admin.controller.dto.response.PageResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogDetailResponse;
import com.project.admin.controller.dto.response.techblog.TechBlogListResponse;
import com.project.admin.controller.spec.TechBlogApi;
import com.project.admin.service.SummaryService;
import com.project.admin.service.TechBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/techblog")
public class TechBlogController implements TechBlogApi {

    private final TechBlogService techBlogService;

    @GetMapping
    public ResponseEntity<PageResponse<TechBlogListResponse>> getTechBlogs(
            @RequestParam Status status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<TechBlogListResponse> results = techBlogService.getTechBlogs(status, page, size)
                .map(TechBlogListResponse::of);

        PageResponse<TechBlogListResponse> result = PageResponse.of(results, page);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechBlogDetailResponse> getTechBlog(@PathVariable Long id) {
        TechBlogDetailResponse response = TechBlogDetailResponse.of(
                techBlogService.getTechBlog(id));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/status")
    public ResponseEntity<Void> updateStatus(@RequestBody TechBlogUpdateStatusRequest request) {
        techBlogService.updateStatus(request.ids(), request.status());
        return ResponseEntity.ok().build();
    }

}
