package com.project.crawler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "crawlerExecutor")
    public Executor  crawlerExecutor() {
       return Executors.newVirtualThreadPerTaskExecutor();
    }
}