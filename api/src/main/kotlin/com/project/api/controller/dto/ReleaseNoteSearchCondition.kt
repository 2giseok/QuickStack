package com.project.api.controller.dto

import com.project.api.constants.Category
import com.project.api.constants.TechStack
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class ReleaseNoteSearchCondition(

    @field:NotNull(message = "Current page must not be null")
    @field:Min(value = 1, message = "Current page must be greater than 0")
    var page: Int = 1,

    @field:NotNull(message = "Page size must not be null")
    @field:Min(value = 1, message = "Page size must be greater than 0")
    @field:Max(value = 20, message = "Page size must be less than or equal to 20")
    var size: Int = 20,
    var techStack: TechStack? = null,
    var category: Category = Category.ALL,

    var keyword: String? = null,
)
