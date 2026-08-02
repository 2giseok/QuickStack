package com.project.crawler.service;

import com.project.crawler.entity.TechBlog;
import com.project.crawler.repositroy.TechBlogRepository;
import com.project.crawler.util.TrackExecutionTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlStorageService {

    private final CrawlService crawlService;

    private final CrawlerContentService crawlerContentService;
    private final TechBlogRepository techBlogRepository;


    @TrackExecutionTime
    public void crawl(String url) {
        Optional<TechBlog> target = techBlogRepository.findByUrl(url);

        if(target.isEmpty()) {
            log.info("저장되지 않은 URL={}",url);
            return;
        }

        TechBlog techBlog = target.get();
        try {
            String content = crawlService.crawl(url);
            crawlerContentService.saveSuccess(techBlog, content);
        } catch (HttpClientErrorException.Forbidden e) {
            log.warn("403 url={}", url);
            crawlerContentService.handle403(techBlog);
        } catch (Exception e) {
            log.warn("크롤링 실패 url ={}", url);
            log.info("오류 ={}", e.getMessage());
            crawlerContentService.handleTimeout(techBlog, e.getClass().getSimpleName());
        }
    }
}
