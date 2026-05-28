package com.project.api.controller.dto

import com.project.api.constants.Region
import com.project.api.service.dto.CompanyResult
import com.project.api.service.dto.CompanyResults
import java.time.LocalDateTime

data class CompanyListResponse(
    val companies: List<CompanyDetailResponse>,
) {
    companion object {
        fun from(results: CompanyResults) = CompanyListResponse(
            companies = results.companies.map { CompanyDetailResponse.from(it) },
        )
    }
}

data class CompanyDetailResponse(
    val name: String,
    val ko: String,
    val en: String,
    val region: Region,
    val totalPostCount: Long,
    val lastPostedAt: LocalDateTime?,
) {
    companion object {
        fun from(result: CompanyResult) = CompanyDetailResponse(
            name = result.name,
            ko = result.ko,
            en = result.en,
            region = result.region,
            totalPostCount = result.totalPostCount,
            lastPostedAt = result.lastPostedAt,
        )
    }
}

data class PopularCompanyListResponse(
    val companies: List<CompanyResponse>,
) {
    companion object {
        fun from(results: CompanyResults) = PopularCompanyListResponse(
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
