package com.malgn.configure;



import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringDocConfiguration implements WebMvcConfigurer {

    @Bean
    public OpenAPI info(){
        // 문서의 대표 정보 생성
        Info info = new Info()
            .version("0.0.1")
            .title("simple-cms-api")
            .description("2026 신입 Back-End 개발자 코딩 과제 - 간단한 CMS REST API 명세서");

        String jwtHeaderName = "Authorization"; // 헤더 이름
        SecurityRequirement requirement = new SecurityRequirement().addList(jwtHeaderName); // 보안 요구사항 객체

        Components components = new Components()
            .addSecuritySchemes(
                jwtHeaderName,
                new SecurityScheme()
                        .name(jwtHeaderName) // 헤더 이름
                        .type(SecurityScheme.Type.HTTP) // 통신유형
                        .scheme("bearer") // 토큰의 종류
                        .bearerFormat("JWT")
        );

        return new OpenAPI()
                .info(info)
                .addSecurityItem(requirement)
                .components(components);
    }
}