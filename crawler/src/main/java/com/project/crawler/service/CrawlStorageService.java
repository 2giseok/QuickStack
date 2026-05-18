package com.project.crawler.service;

import com.project.crawler.entity.TechBlog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlStorageService {

    private final CrawlService crawlService;

    private final CrawlerContentService crawlerContentService;

    @Async("crawlerExecutor")
    public void crawl(TechBlog techBlog) {
        try {
            String content = crawlService.crawl(techBlog.getUrl());
            crawlerContentService.saveSuccess(techBlog, content);
        } catch (HttpClientErrorException.Forbidden e) {
            log.warn("403 url={}", techBlog.getUrl());
            crawlerContentService.handle403(techBlog);
        } catch (Exception e) {
            log.warn("크롤링 실패 url ={}", techBlog.getUrl());
            crawlerContentService.handleTimeout(techBlog, e.getClass().getSimpleName());
        }
    }
}
