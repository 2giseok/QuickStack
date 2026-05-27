package com.project.admin.repository.quertdsl;

import com.project.admin.constant.Source;
import com.project.admin.constant.Status;
import com.project.admin.domain.entity.TechBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechBlogRepositoryCustom {
    Page<TechBlog> findByStatusAndSource(
            Status status,
            Source source,
            Pageable pageable
    );

}
