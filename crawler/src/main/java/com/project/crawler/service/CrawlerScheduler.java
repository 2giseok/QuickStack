package com.project.crawler.service;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.repositroy.TechBlogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CrawlerScheduler {

    private final TechBlogRepository techBlogRepository;

    private final CrawlStorageService crawlStorageService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void scheduleCrawl() {
        List<TechBlog> techBlogs = techBlogRepository.findByStatusAndLimitSource(
                Status.PENDING, 1, 10
        );
        if (techBlogs.isEmpty()) {
            return;
        }
        log.info("크롤링 대상  url={}", techBlogs.size());
        techBlogs.forEach(crawlStorageService::crawl);
    }
}
