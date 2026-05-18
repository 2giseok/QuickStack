package com.project.admin.service;

import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import com.project.admin.repository.TechBlogApprovalRepository;
import com.project.admin.repository.TechBlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class TechBlogApprovalService {

    private final TechBlogApprovalRepository techBlogApprovalRepository;
    private final TechBlogRepository techBlogRepository;


    @Transactional
    public void save(Long techBlogId, String summary) {
        TechBlog techBlog = techBlogRepository.findById(techBlogId).orElseThrow();

        TechBlogApproval approval = techBlogApprovalRepository
                .findByTechBlogId(techBlogId)
                .orElse(new TechBlogApproval(techBlog));

        approval.update(summary);

        techBlogApprovalRepository.save(approval);
    }

}
