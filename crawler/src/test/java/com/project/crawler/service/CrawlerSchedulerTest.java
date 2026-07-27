package com.project.crawler.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.repositroy.TechBlogRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class CrawlerSchedulerTest {

    @Mock
    private  TechBlogRepository techBlogRepository;

    @Mock
    private  RabbitTemplate rabbitTemplate;

    @InjectMocks
    private CrawlerScheduler crawlerScheduler;

    private TechBlog createTechBlog(Long id, String url) {
        return TechBlog.builder()
                .id(id)
                .url(url)
                .status(Status.PENDING)
                .publishedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void 재전송_대상_없을시_전송X() {
        when(techBlogRepository.findByStatusAndCreatedAtBefore(eq(Status.PENDING),any(LocalDateTime.class)))
                .thenReturn(List.of());

        crawlerScheduler.scheduleCrawl();

        verifyNoInteractions(rabbitTemplate);
    }

    @Test
    void 재전송_대상_있으면_전송() {

        String url = "https://example.com/post/1";
        String url2 = "https://example.com/post/2";
        TechBlog techBlog = createTechBlog(1L, url);
        TechBlog techBlog2 = createTechBlog(2L, url2);
        when(techBlogRepository.findByStatusAndCreatedAtBefore(eq(Status.PENDING),any(LocalDateTime.class)))
                .thenReturn(List.of(techBlog,techBlog2));

        crawlerScheduler.scheduleCrawl();

        verify(rabbitTemplate).convertAndSend("crawlerExchange","crawlRouting",techBlog.getUrl());
        verify(rabbitTemplate).convertAndSend("crawlerExchange","crawlRouting",techBlog2.getUrl());
    }


    @Test
    void 일부_전송_실패() {

        String url = "https://example.com/post/1";
        String url2 = "https://example.com/post/2";
        TechBlog techBlog = createTechBlog(1L, url);
        TechBlog techBlog2 = createTechBlog(2L, url2);
        when(techBlogRepository.findByStatusAndCreatedAtBefore(eq(Status.PENDING),any(LocalDateTime.class)))
                .thenReturn(List.of(techBlog,techBlog2));

        doThrow(new RuntimeException("연결 실패"))
                .when(rabbitTemplate).convertAndSend("crawlerExchange","crawlRouting",techBlog.getUrl());

        crawlerScheduler.scheduleCrawl();

        verify(rabbitTemplate).convertAndSend("crawlerExchange","crawlRouting",techBlog2.getUrl());
    }
}