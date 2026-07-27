package com.project.admin.repository;

import com.project.admin.constant.Status;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.repository.quertdsl.TechBlogRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Collection;
import java.util.List;

public interface TechBlogRepository extends
        JpaRepository<TechBlog, Long>
        , TechBlogRepositoryCustom
{

    Page<TechBlog> findByStatus(Status status, Pageable pageable);

    @Modifying
    @Query("UPDATE TechBlog t SET t.status = :status WHERE t.id IN :ids")
    void updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Status status);

    @Query("SELECT t.url FROM TechBlog t WHERE t.id IN :ids")
    List<String> findUrlsByIds(@Param("ids") List<Long> ids);

    @Query("""
            SELECT t.id FROM TechBlog t
            LEFT JOIN TechBlogApproval a ON a.techBlog.id = t.id
            WHERE t.status = :status
            AND (a.summary IS NULL OR a.id IS NULL )
            """)
    List<Long> findUnpublishedUrlWithOutSummary(@Param("status") Status status);

    List<TechBlog> findAllByUrlIn(Collection<String> urls);

    List<Long> id(Long id);
}
