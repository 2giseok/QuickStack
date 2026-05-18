package com.project.admin.controller.dto.response;

import org.springframework.data.domain.Page;
import java.util.List;

public record PageResponse<T>(
        List<T> content,
        Pagination  pagination

) {
    public  record Pagination(
            long totalCount,
            int pageSize,
            int currentPage
    ){}

    public static <T> PageResponse<T> of(Page<T> page, int currentPage) {

        return new PageResponse<>(
                page.getContent(),
                new Pagination(
                        page.getTotalPages(),
                        page.getSize(),
                        currentPage
                )
        );
    }

}
