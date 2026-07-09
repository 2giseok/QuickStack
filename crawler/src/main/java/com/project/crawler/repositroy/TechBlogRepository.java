package com.project.crawler.repositroy;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TechBlogRepository extends JpaRepository<TechBlog, Long> {

    Optional<TechBlog> findByUrl(String url);

    @Query("select t from TechBlog  t where t.status= :status AND t.publishedAt < :nowTime")
    List<TechBlog> findByStatusAndCreatedAtBefore(Status status, LocalDateTime nowTime);
}
