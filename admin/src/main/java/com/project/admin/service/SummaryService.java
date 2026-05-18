package com.project.admin.service;

import com.project.admin.ai.AiService;
import com.project.admin.constant.CrawlStatus;
import com.project.admin.domain.entity.TechBlogContent;
import com.project.admin.domain.exception.summary.SummaryErrorCode;
import com.project.admin.domain.exception.summary.SummaryException;
import com.project.admin.repository.TechBlogContentRepository;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SummaryService {

    private final AiService aiService;
    private final TechBlogContentRepository techBlogContentRepository;
    private final TechBlogApprovalService techBlogApprovalService;

    @Async("summaryExecutor")
    public CompletableFuture<String> summarize(Long id) {
        try {
            TechBlogContent content = techBlogContentRepository.
                    findLatestByTechBlogIdAndStatus(id, CrawlStatus.SUCCESS)
                    .orElseThrow(() -> new SummaryException(SummaryErrorCode.CONTENT_NOT_FOUND));

            String summary = aiService.summarize(content.getContent());
            techBlogApprovalService.save(id, summary);

            return CompletableFuture.completedFuture(summary);
        } catch (SummaryException e) {
            return CompletableFuture.failedFuture(e);
        } catch (HttpServerErrorException e) {
            log.warn("AI 서버 오류 techBlogId={}", id);
            return CompletableFuture.failedFuture(
                    new SummaryException(SummaryErrorCode.AI_UNAVAILABLE));
        } catch (Exception e) {
            log.warn("요약 실패 techBlogId={}", id);
            return CompletableFuture.failedFuture(
                    new SummaryException(SummaryErrorCode.SUMMARY_FAILED));

        }
    }
}
