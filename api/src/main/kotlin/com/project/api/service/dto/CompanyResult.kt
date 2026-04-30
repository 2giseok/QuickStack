package com.project.api.service.dto

import com.project.api.constants.Source

data class CompanyResults(
    val companies: List<CompanyResult>,
)

data class CompanyResult(
    val name: String,
    val ko: String,
    val en: String,
) {
    companion object {
        fun from(source: Source) = CompanyResult(
            name = source.name,
            ko = source.ko,
            en = source.en,
        )
    }
}