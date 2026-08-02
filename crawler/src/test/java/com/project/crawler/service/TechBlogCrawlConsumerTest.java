package com.project.crawler.service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TechBlogCrawlConsumerTest {

    @Mock
    private CrawlStorageService crawlStorageService;

    @InjectMocks
    private TechBlogCrawlConsumer consumer;


    @Test
    void 메시지_도착시_크롤링_호출() {
        String url = "https://example.com/post";

        consumer.consumerCrawl(url);

        verify(crawlStorageService).crawl(url);
    }

}