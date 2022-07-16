package com.kyy.api.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // ApiSelectorBuilder 인스턴스 return
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class)) // 해당 어노테이션이 붙은 것만 내용을 읽음
                // .apis(RequestHandlerSelectors.basePackage("com.kyy.api.controller")) // 처럼 사용도 가능
                .paths(PathSelectors.any())
                .build();
    }
}
