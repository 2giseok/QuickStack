package com.project.api.service.dto

import com.project.api.constants.Region
import com.project.api.constants.Source
import com.project.api.entity.company.CompanyMappingResult
import com.project.api.entity.company.PopularCompanyMappingResult
import java.time.LocalDateTime

data class CompanyResults(
    val companies: List<CompanyResult>,
)

data class CompanyResult(
    val name: String,
    val ko: String,
    val en: String,
    val region: Region,
    val url: String,
    val totalPostCount: Long,
    val lastPostedAt: LocalDateTime?,
) {
    companion object {
        fun of(source: Source, mappingResult: CompanyMappingResult?) = CompanyResult(
            name = source.name,
            ko = source.ko,
            en = source.en,
            region = source.region,
            url = source.url,
            totalPostCount = mappingResult?.totalCount ?: 0L,
            lastPostedAt = mappingResult?.lastPostedAt,
        )

        fun from(mappingResult: PopularCompanyMappingResult) = CompanyResult(
            name = mappingResult.source.name,
            ko = mappingResult.source.ko,
            en = mappingResult.source.en,
            region = mappingResult.source.region,
            url = mappingResult.source.url,
            totalPostCount = mappingResult.totalCount,
            lastPostedAt = mappingResult.lastPostedAt,
        )
    }
}
