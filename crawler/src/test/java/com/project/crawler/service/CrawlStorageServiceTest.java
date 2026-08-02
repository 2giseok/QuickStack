package com.project.crawler.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.repositroy.TechBlogRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(MockitoExtension.class)
class CrawlStorageServiceTest {

    @Mock
    private  CrawlService crawlService;

    @Mock
    private  CrawlerContentService crawlerContentService;
    @Mock
    private  TechBlogRepository techBlogRepository;

    @InjectMocks
    private CrawlStorageService crawlStorageService;


    private TechBlog createTechBlog(Long id, String url) {
        return TechBlog.builder()
                .id(id)
                .url(url)
                .status(Status.PENDING)
                .publishedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void  저장되지않은_URL() {
        String url = "https://example.com";
    //    when(techBlogRepository.findByUrl(url)).thenReturn(Optional.empty());
        crawlStorageService.crawl(url);
        verifyNoInteractions(crawlService, crawlerContentService);
    }

    @Test
    void  크롤링_성공() {
        String url = "https://example.com";
        TechBlog techBlog = createTechBlog(1L, url);

        when(techBlogRepository.findByUrl(techBlog.getUrl())).thenReturn(Optional.of(techBlog));
        when(crawlService.crawl(techBlog.getUrl())).thenReturn("본문");
        crawlStorageService.crawl(techBlog.getUrl());
        verify(crawlerContentService).saveSuccess(techBlog,"본문");

    }

    @Test
    void 크롤링_실패_TimeOut() {
        String url = "https://example.com";
        TechBlog techBlog = createTechBlog(1L, url);

        when(techBlogRepository.findByUrl(techBlog.getUrl())).thenReturn(Optional.of(techBlog));
        when(crawlService.crawl(techBlog.getUrl())).thenThrow(new RuntimeException("타임 아웃"));

        crawlStorageService.crawl(techBlog.getUrl());
        verify(crawlerContentService).handleTimeout(eq(techBlog),eq("RuntimeException"));

    }

    @Test
    void 크롤링_실패_Forbidden() {
        String url = "https://example.com";
        TechBlog techBlog = createTechBlog(1L, url);

        when(techBlogRepository.findByUrl(techBlog.getUrl())).thenReturn(Optional.of(techBlog));
        when(crawlService.crawl(techBlog.getUrl())).thenThrow( HttpClientErrorException.Forbidden.class);

        crawlStorageService.crawl(techBlog.getUrl());
        verify(crawlerContentService).handle403(techBlog);

    }

}