package com.project.crawler.service;

import com.project.crawler.constants.CrawlStatus;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.entity.TechBlogContent;
import com.project.crawler.repositroy.TechBlogContentRepository;
import com.project.crawler.repositroy.TechBlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CrawlerContentService {

    private final TechBlogContentRepository techBlogContentRepository;
    private final TechBlogRepository techBlogRepository;

    @Transactional
    public void saveSuccess(TechBlog techBlog, String content) {
        TechBlogContent entity = techBlogContentRepository
                .findById(techBlog.getId())
                .orElse(TechBlogContent.init(techBlog));

        entity.updateSuccess(content);
        techBlogContentRepository.save(entity);
        techBlog.markAsUnpublished();
        techBlogRepository.save(techBlog);
    }

    @Transactional
    public void handle403(TechBlog techBlog) {
        TechBlogContent entity = techBlogContentRepository
                .findById(techBlog.getId())
                .orElse(TechBlogContent.init(techBlog));

        entity.updateFailed(CrawlStatus.FAILED_403, "403 FORBIDDEN");
        techBlogContentRepository.save(entity);
        techBlog.markAsFailed();
        techBlogRepository.save(techBlog);
    }

    @Transactional
    public void handleTimeout(TechBlog techBlog, String errorName) {
        TechBlogContent entity = techBlogContentRepository
                .findById(techBlog.getId())
                .orElse(TechBlogContent.init(techBlog));

        entity.updateFailed(CrawlStatus.FAILED_TIMEOUT, errorName);
        techBlogContentRepository.save(entity);
    }
}
