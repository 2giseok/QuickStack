package com.project.crawler.service;

import com.project.crawler.constants.Status;
import com.project.crawler.entity.TechBlog;
import com.project.crawler.repositroy.TechBlogRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CrawlerScheduler {

    private final TechBlogRepository techBlogRepository;

    private final RabbitTemplate rabbitTemplate;

    @Scheduled(cron = "${batch.cron.crawl}")
    public void scheduleCrawl() {
        LocalDateTime threeHourAgo = LocalDateTime.now().minusHours(3);
        List<TechBlog> techBlogs = techBlogRepository.findByStatusAndCreatedAtBefore(
                Status.PENDING, threeHourAgo
        );
        if (techBlogs.isEmpty()) {
            return;
        }

        log.info("재전송 시작 수={}",techBlogs.size());
        for (TechBlog techBlog : techBlogs) {
            try {
                rabbitTemplate.convertAndSend("crawlerExchange","crawlRouting",techBlog.getUrl());
            } catch (Exception e) {
                log.error("재전송 실패 URL={}, ERROR={}",techBlog.getUrl(),e.getMessage());
            }
        }
    }
}