package com.project.crawler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TechBlogCrawlConsumer {

    private final CrawlStorageService crawlStorageService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "crawlerQueue", durable = "true"),
            exchange = @Exchange(value = "crawlerExchange", type = "direct"),
            key = "crawlRouting"
    ))
    public void consumerCrawl(String url) {
        log.info("RabbitMQ 시작, url={}", url);

        crawlStorageService.crawl(url);
    }

}
