package com.project.api.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
class SecurityConfig(
    @Qualifier(value = "corsConfigurationSource") private val cors: CorsConfigurationSource,
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.configurationSource(cors) }
            .csrf { it.disable() }  //TODO 추후 JWT 인증 도입 시 고려해 CSRF 토큰 비활성화 처리
            .authorizeHttpRequests { it.anyRequest().permitAll() }
        return http.build()
    }
}