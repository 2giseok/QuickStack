package com.project.admin.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return  new OpenAPI()
                .addServersItem(new Server().url(("/admin")))
                .info(new Info()
                        .title("관리자 API")
                        .description("관리자 API 명세서"));
    }
}
