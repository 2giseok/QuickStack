package com.project.api.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info().apply {
                    title = "QuickStack API Documentation"
                    description = "QuickStack REST API 문서입니다."
                    version = "v1.0.0"
                    license = License().name("Apache 2.0").url("http://springdoc.org")
                }
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Swagger Documentation")
            )
    }
}