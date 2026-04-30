package com.project.api.controller.dto

import com.project.api.constants.Category

data class CategoryResponseList(
    val categories: List<CategoryResponse>,
) {
    companion object {
        fun from(categories: List<Category>) = CategoryResponseList(
            categories = categories.map { CategoryResponse(code = it.code, display = it.display) },
        )
    }
}

data class CategoryResponse(
    val code: String,
    val display: String,
)