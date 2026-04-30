package com.project.api.service.dto

import org.springframework.data.domain.Page

data class PageResult(
    val totalCount: Long,
    val pageSize: Int,
    val currentPage: Int,
) {
    companion object {
        fun from(page: Page<*>) = PageResult(
            totalCount = page.totalElements,
            pageSize = page.size,
            currentPage = page.number + 1,
        )
    }
}