package com.project.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.ZoneId

@Configuration
class ClockConfig {

    companion object {
        val zone: ZoneId = ZoneId.of("Asia/Seoul")
    }

    @Bean
    fun clock(): Clock {
        return Clock.system(zone)
    }
}