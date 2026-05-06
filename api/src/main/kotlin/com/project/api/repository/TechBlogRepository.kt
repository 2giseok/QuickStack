package com.project.api.repository

import com.project.api.constants.Source
import com.project.api.entity.TechBlog
import com.project.api.entity.company.CompanyMappingResult
import com.project.api.entity.company.PopularCompanyMappingResult
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TechBlogRepository : JpaRepository<TechBlog, Long> {

    @Query(
        """
        SELECT t FROM TechBlog t
        WHERE t.status = com.project.api.constants.Status.PUBLISHED
          AND (:keyword IS NULL OR t.title LIKE %:keyword%)
        ORDER BY t.publishedAt DESC
    """
    )
    fun findByKeyword(
        @Param("keyword") keyword: String?,
        pageable: Pageable,
    ): Page<TechBlog>

    @Query(
        """
        SELECT t FROM TechBlog t
        WHERE t.status = com.project.api.constants.Status.PUBLISHED
          AND t.source = :source
        ORDER BY t.publishedAt DESC
    """
    )
    fun findBySource(
        @Param("source") source: Source,
        pageable: Pageable,
    ): Page<TechBlog>

    @Query(
        """
        SELECT NEW com.project.api.entity.company.CompanyMappingResult(t.source, COUNT(t), MAX(t.publishedAt))
        FROM TechBlog t
        WHERE t.status = com.project.api.constants.Status.PUBLISHED
        GROUP BY t.source
    """
    )
    fun findCompanies(): List<CompanyMappingResult>

    @Query(
        """
        SELECT NEW com.project.api.entity.company.PopularCompanyMappingResult(t.source, COUNT(t), MAX(t.publishedAt))
        FROM TechBlog t
        WHERE t.status = com.project.api.constants.Status.PUBLISHED
          AND t.source IN :sources
        GROUP BY t.source
    """
    )
    fun findPopularCompanies(@Param("sources") sources: List<Source>): List<PopularCompanyMappingResult>
}
