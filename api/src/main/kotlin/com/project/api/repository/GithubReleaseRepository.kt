package com.project.api.repository

import com.project.api.constants.TechStack
import com.project.api.entity.GithubRelease
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GithubReleaseRepository : JpaRepository<GithubRelease, Long> {

    @Query(
        """
        SELECT g FROM GithubRelease g
        WHERE g.status = com.project.api.constants.Status.PUBLISHED
          AND g.draft = false
          AND g.prerelease = false
          AND (:#{#techStacks.size()} = 0 OR g.techStack IN :techStacks)
        ORDER BY g.publishedAt DESC
    """
    )
    fun findByTechStacks(
        @Param("techStacks") techStacks: Set<TechStack>,
        pageable: Pageable,
    ): Page<GithubRelease>


    @Query(
            """
    SELECT g FROM GithubRelease g
    WHERE g.status = com.project.api.constants.Status.PUBLISHED
      AND g.draft = false
      AND g.prerelease = false
      AND (:#{#techStacks.size()} = 0 OR g.techStack IN :techStacks)
      AND (
        :keyword IS NULL
        OR (:#{#matchedTechStacks.size()} > 0 AND g.techStack IN :matchedTechStacks)
        OR g.name LIKE CONCAT('%', :keyword, '%')
        OR g.tagName LIKE CONCAT('%', :keyword, '%')
      )
    ORDER BY g.publishedAt DESC
        """
    )
    fun findByTechStacksAndKeyword(
        @Param("techStacks") techStacks: Set<TechStack>,
        @Param("matchedTechStacks") matchedTechStacks: Set<TechStack>,
        @Param("keyword") keyword: String?,
        pageable: Pageable,
    ): Page<GithubRelease>
}