package com.project.api.controller.dto

import com.project.api.service.dto.CompanyResult
import com.project.api.service.dto.CompanyResults

data class CompanyListResponse(
    val companies: List<CompanyResponse>,
) {
    companion object {
        fun from(results: CompanyResults) = CompanyListResponse(
            companies = results.companies.map { CompanyResponse.from(it) },
        )
    }
}

data class CompanyResponse(
    val name: String,
    val ko: String,
    val en: String,
) {
    companion object {
        fun from(result: CompanyResult) = CompanyResponse(
            name = result.name,
            ko = result.ko,
            en = result.en,
        )
    }
}