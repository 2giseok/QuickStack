package com.project.crawler.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.crawler.constants.CrawlStatus;
import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.entity.TechBlogContent;
import com.project.crawler.repositroy.TechBlogContentRepository;
import com.project.crawler.repositroy.TechBlogRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CrawlerContentServiceTest {

    @Mock
    private  TechBlogContentRepository techBlogContentRepository;
    @Mock
    private  TechBlogRepository techBlogRepository;

    @InjectMocks
    private CrawlerContentService crawlerContentService;


    private TechBlog createTechBlog(Long id, String url) {
        return TechBlog.builder()
                .id(id)
                .url(url)
                .status(Status.PENDING)
                .publishedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void saveSuccess_세로운_컨텐츠() {
        String url = "https://example.com/post";
        TechBlog techBlog = createTechBlog(1L, url);
        when(techBlogContentRepository.findById(1L)).thenReturn(Optional.empty());

        crawlerContentService.saveSuccess(techBlog,"본문 내용");

        ArgumentCaptor<TechBlogContent> captor = ArgumentCaptor.forClass(TechBlogContent.class);
        verify(techBlogContentRepository).save(captor.capture());

        TechBlogContent saved = captor.getValue();
        assertThat(saved.getContent()).isEqualTo("본문 내용");

    }

    @Test
    void saveSuccess_기존에_존재하는_컨텐츠() {
        String url = "https://example.com/post";
        TechBlog techBlog = createTechBlog(1L, url);
        TechBlogContent init = TechBlogContent.init(techBlog);
        when(techBlogContentRepository.findById(1L)).thenReturn(Optional.of(init));

        crawlerContentService.saveSuccess(techBlog,"업데이트된 본문");

        verify(techBlogContentRepository).save(init);
        assertThat(init.getContent()).isEqualTo("업데이트된 본문");
    }


    @Test
    void Error403() {

        String url = "https://example.com/post";
        TechBlog techBlog = createTechBlog(1L, url);

        when(techBlogContentRepository.findById(1L)).thenReturn(Optional.empty());

        crawlerContentService.handle403(techBlog);


        ArgumentCaptor<TechBlogContent> captor = ArgumentCaptor.forClass(TechBlogContent.class);
        verify(techBlogContentRepository).save(captor.capture());

        TechBlogContent saved = captor.getValue();
        assertThat(saved.getStatus()).isEqualTo(CrawlStatus.FAILED_403);
        assertThat(saved.getErrorName()).isEqualTo("403 FORBIDDEN");

    }

    @Test
    void handleTimeout() {
        String url = "https://example.com/post";
        TechBlog techBlog = createTechBlog(1L, url);

        when(techBlogContentRepository.findById(1L)).thenReturn(Optional.empty());

        crawlerContentService.handleTimeout(techBlog,"timeOut");


        ArgumentCaptor<TechBlogContent> captor = ArgumentCaptor.forClass(TechBlogContent.class);
        verify(techBlogContentRepository).save(captor.capture());

        TechBlogContent saved = captor.getValue();
        assertThat(saved.getStatus()).isEqualTo(CrawlStatus.FAILED_TIMEOUT);
        assertThat(saved.getErrorName()).isEqualTo("timeOut");

    }
}