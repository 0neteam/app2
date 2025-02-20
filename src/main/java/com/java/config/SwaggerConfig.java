package com.java.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    // Swagger UI 화면 표시 설정
    private Info apiInfo() {
        return new Info()
                .title("제조측 시스템 관리")
                .description("REST API 관한 요청 테스트")
                .version("1.0.0");
    }

    // Swagger 빈 등록
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(apiInfo());
    }
    
}
