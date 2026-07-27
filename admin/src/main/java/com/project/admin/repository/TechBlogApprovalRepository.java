package com.project.admin.repository;

import com.project.admin.domain.entity.TechBlogApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TechBlogApprovalRepository extends JpaRepository<TechBlogApproval, Long> {
    Optional<TechBlogApproval> findByTechBlogId(Long id);
    List<TechBlogApproval> findAllByTechBlogIdIn(List<Long> list);

    List<Long> id(Long id);
}
