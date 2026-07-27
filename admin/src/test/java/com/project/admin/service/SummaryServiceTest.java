package com.project.admin.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.project.admin.ai.AiService;
import com.project.admin.constant.CrawlStatus;
import com.project.admin.domain.entity.TechBlogContent;
import com.project.admin.domain.exception.summary.SummaryErrorCode;
import com.project.admin.domain.exception.summary.SummaryException;
import com.project.admin.repository.TechBlogContentRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpServerErrorException;

@ExtendWith(MockitoExtension.class)
class SummaryServiceTest {

    @Mock
    private TechBlogContentRepository techBlogContentRepository;
    @Mock
    private AiService aiService;
    @Mock
    private TechBlogApprovalService techBlogApprovalService;
    @InjectMocks
    private SummaryService summaryService;

    private TechBlogContent createContent(String content) {
        return TechBlogContent.builder()
                .id(1L)
                .content(content)
                .status(CrawlStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();
    }


    @Test
    void summarize_성공() throws Exception {
        Long id =1L;
        TechBlogContent content = createContent("본문");
        when(techBlogContentRepository.findLatestByTechBlogIdAndStatus(id,CrawlStatus.SUCCESS))
                .thenReturn(Optional.of(content));
        when(aiService.summarize("본문")).thenReturn("요약결과");

        CompletableFuture<String>result = summaryService.summarize(id);

        assertThat(result.get()).isEqualTo("요약결과");

    }
    @Test
    void summarize_본문_없을시_에러() throws Exception {
        Long id =1L;

        when(techBlogContentRepository.findLatestByTechBlogIdAndStatus(id,CrawlStatus.SUCCESS))
                .thenReturn(Optional.empty());

        CompletableFuture<String>result = summaryService.summarize(id);

        assertThat(result.isCompletedExceptionally()).isTrue();
        assertThatThrownBy(result::get)
                .cause()
                .isInstanceOf(SummaryException.class)
                .extracting("errorCode")
                .isEqualTo(SummaryErrorCode.CONTENT_NOT_FOUND);

    }

    @Test
    void summarize_AI_서버_오류() throws Exception {
        Long id =1L;
        TechBlogContent content = createContent("본문");
        when(techBlogContentRepository.findLatestByTechBlogIdAndStatus(id,CrawlStatus.SUCCESS))
                .thenReturn(Optional.of(content));
        when(aiService.summarize("본문")).thenThrow(HttpServerErrorException.class);

        CompletableFuture<String>result = summaryService.summarize(id);

        assertThatThrownBy(result::get)
                .cause()
                .isInstanceOf(SummaryException.class)
                .extracting("errorCode")
                .isEqualTo(SummaryErrorCode.AI_UNAVAILABLE);
    }

    @Test
    void summarize_그외_오류() throws Exception {
        Long id =1L;
        TechBlogContent content = createContent("본문");
        when(techBlogContentRepository.findLatestByTechBlogIdAndStatus(id,CrawlStatus.SUCCESS))
                .thenReturn(Optional.of(content));
        when(aiService.summarize("본문")).thenThrow(Exception.class);

        CompletableFuture<String>result = summaryService.summarize(id);

        assertThatThrownBy(result::get)
                .cause()
                .isInstanceOf(SummaryException.class)
                .extracting("errorCode")
                .isEqualTo(SummaryErrorCode.SUMMARY_FAILED);
    }
}