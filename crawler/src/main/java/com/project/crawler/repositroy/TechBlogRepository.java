package com.project.crawler.repositroy;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import java.util.List;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechBlogRepository extends JpaRepository<TechBlog, Long> {

    @Query("""
            SELECT t FROM TechBlog t
            WHERE t.status = :status
            AND (
            SELECT COUNT(t2) FROM TechBlog t2
            WHERE t2.source = t.source
            AND t2.status = :status
            AND t2.publishedAt > t.publishedAt
            ) < :limitSource
            ORDER BY t.publishedAt DESC
            """)
    List<TechBlog> findByStatusAndLimitSource(
            @Param("status") Status status,
            @Param("limitSource") int limitSource,
            Limit limit
    );
}
