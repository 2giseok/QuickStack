package com.project.admin.controller.spec;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "summary")
public interface SummaryApi {

    @Operation(summary = "요약 버튼",
            parameters = {
                    @Parameter(name = "techBlogId", description = "AI 요약 ,UNPUBLISHED 상태일때만 실행 가능", example = "317948546257846273")
            }
    )
    public ResponseEntity<String> summary(@PathVariable String techBlogId);
}
