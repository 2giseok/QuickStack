package com.project.admin.repository;

import com.project.admin.constant.CrawlStatus;
import com.project.admin.domain.entity.TechBlogContent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechBlogContentRepository extends JpaRepository<TechBlogContent, Long> {

    @Query("""
            SELECT c FROM TechBlogContent c
            WHERE c.techBlog.id =  :techBlogId
            AND c.status = :status
            ORDER BY c.createdAt DESC
            LIMIT 1
            """)
    Optional<TechBlogContent> findLatestByTechBlogIdAndStatus(
            @Param("techBlogId") Long techBlogId,
            @Param("status") CrawlStatus status);

}
