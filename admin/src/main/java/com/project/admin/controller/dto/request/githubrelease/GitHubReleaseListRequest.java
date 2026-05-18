package com.project.admin.controller.dto.request.githubrelease;

import com.project.admin.constant.Status;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.RequestParam;

public record GitHubReleaseListRequest(
        Status status,

        @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다")
        @RequestParam(defaultValue = "1")
        int page,

        @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다")
        @Max(value = 20, message = "페이지 크기는 20 이하이어야 합니다")
        @RequestParam(defaultValue = "20")
        int size
) {

}
