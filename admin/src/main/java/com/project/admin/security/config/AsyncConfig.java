package com.project.admin.security.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "summaryExecutor")
    public Executor summaryExecutor() {
       return Executors.newVirtualThreadPerTaskExecutor();
    }
}