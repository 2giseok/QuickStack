package com.project.admin.service;

import com.project.admin.constant.Status;
import com.project.admin.controller.dto.request.techblog.TechBlogListRequest;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import com.project.admin.domain.exception.techblog.TechBlogErrorCode;
import com.project.admin.domain.exception.techblog.TechBlogException;
import com.project.admin.repository.TechBlogApprovalRepository;
import com.project.admin.repository.TechBlogRepository;
import com.project.admin.service.dto.techblog.TechBlogDetailResult;
import com.project.admin.service.dto.techblog.TechBlogResult;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TechBlogService {

    private final TechBlogRepository techBlogRepository;
    private final TechBlogApprovalRepository techBlogApprovalRepository;

    public Page<TechBlogResult> getTechBlogs(TechBlogListRequest request) {
        Pageable pageable = PageRequest.of(
                request.page() - 1,
                request.size(),
                Sort.Direction.DESC,
                "publishedAt");

        return techBlogRepository.findByStatusAndSource(request.status(), request.source(),
                        pageable)
                .map(TechBlogResult::from);
    }

    public TechBlogDetailResult getTechBlog(Long id) {
        TechBlog techBlog = techBlogRepository.findById(id)
                .orElseThrow(() -> new TechBlogException(TechBlogErrorCode.TECH_BLOG_ERROR_CODE));

        TechBlogApproval approval = techBlogApprovalRepository.findByTechBlogId(techBlog.getId())
                .orElse(null);

        return TechBlogDetailResult.from(techBlog, approval);
    }

    @Transactional
    public void updateStatus(List<Long> ids, Status status) {
        techBlogRepository.updateStatusByIds(ids, status);

        List<TechBlog> techBlogs = techBlogRepository.findAllById(ids);

        Map<Long, TechBlogApproval> approvalMap = techBlogApprovalRepository.findAllByTechBlogIdIn(
                        ids)
                .stream()
                .collect(Collectors.toMap(
                        a -> a.getTechBlog().getId(),
                        a -> a
                ));

        List<TechBlogApproval> approvals = techBlogs.stream()
                .map(techBlog -> approvalMap.getOrDefault(
                        techBlog.getId(),
                        new TechBlogApproval(techBlog)
                )).toList();

        techBlogApprovalRepository.saveAll(approvals);
    }
}
