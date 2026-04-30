package com.project.api.controller.dto

import com.project.api.service.dto.PageResult

data class PageResponse(
    val totalCount: Long,
    val pageSize: Int,
    val currentPage: Int,
) {
    companion object {
        fun from(pageResult: PageResult) = PageResponse(
            totalCount = pageResult.totalCount,
            pageSize = pageResult.pageSize,
            currentPage = pageResult.currentPage,
        )
    }
}