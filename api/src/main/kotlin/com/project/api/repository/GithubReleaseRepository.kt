package com.project.api.repository

import com.project.api.constants.TechStack
import com.project.api.entity.GithubRelease
import com.project.api.entity.GithubReleaseId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GithubReleaseRepository : JpaRepository<GithubRelease, GithubReleaseId> {

    @Query(
        """
        SELECT g FROM GithubRelease g
        WHERE g.status = com.project.api.constants.Status.PUBLISHED
          AND g.draft = false
          AND g.prerelease = false
          AND (:#{#techStacks.size()} = 0 OR g.id.techStack IN :techStacks)
        ORDER BY g.publishedAt DESC
    """
    )
    fun findByTechStacks(
        @Param("techStacks") techStacks: Set<TechStack>,
        pageable: Pageable,
    ): Page<GithubRelease>
}