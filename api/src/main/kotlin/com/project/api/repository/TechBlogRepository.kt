package com.project.api.repository

import com.project.api.entity.TechBlog
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface TechBlogRepository : JpaRepository<TechBlog, Long> {

    @Query("""
        SELECT t FROM TechBlog t
        WHERE t.status = com.project.api.constants.Status.PUBLISHED
          AND (:keyword IS NULL OR t.title LIKE %:keyword%)
        ORDER BY t.publishedAt DESC
    """)
    fun findByKeyword(
        @Param("keyword") keyword: String?,
        pageable: Pageable,
    ): Page<TechBlog>
}