package com.project.api.service

import com.project.api.constants.Region
import com.project.api.constants.Source
import com.project.api.repository.TechBlogRepository
import com.project.api.service.dto.CompanyResult
import com.project.api.service.dto.CompanyResults
import com.project.api.service.dto.PageResult
import com.project.api.service.dto.TechBlogResult
import com.project.api.service.dto.TechBlogResults
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TechBlogService(
    private val techBlogRepository: TechBlogRepository,
) {

    fun getCompanies(region: Region? = null): CompanyResults {
        val statsBySource = techBlogRepository.findCompanies()
            .associateBy { it.source }

        val companies = Source.entries
            .filter { region == null || it.region == region }
            .map { source -> CompanyResult.of(source, statsBySource[source]) }

        return CompanyResults(companies = companies)
    }

    fun getPopularCompanies(): CompanyResults {
        val companies = techBlogRepository.findPopularCompanies(Source.popularEntries)
            .map { stat -> CompanyResult.from(stat) }
        return CompanyResults(companies = companies)
    }

    fun getTechBlogs(keyword: String?, page: Int, size: Int): TechBlogResults {
        val pageable = PageRequest.of(page - 1, size)
        val result = techBlogRepository.findByKeyword(keyword?.takeIf { it.isNotBlank() }, pageable)
        return TechBlogResults(
            techBlogs = result.content.map { TechBlogResult.from(it) },
            pagination = PageResult.from(result),
        )
    }

    fun getTechBlogsBySource(sourceCode: String, page: Int, size: Int): TechBlogResults {
        val pageable = PageRequest.of(page - 1, size)
        val source = Source.fromValue(sourceCode) ?: return TechBlogResults.empty(page, size)
        val result = techBlogRepository.findBySource(source, pageable)
        return TechBlogResults(
            techBlogs = result.content.map { TechBlogResult.from(it) },
            pagination = PageResult.from(result),
        )
    }
}
