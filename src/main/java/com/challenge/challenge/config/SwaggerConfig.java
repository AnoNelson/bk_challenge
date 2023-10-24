package com.challenge.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

//@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket swaggerConfiguration() {
        //Prepare a docket instance
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.challenge.challenge"))
                .build()
                .apiInfo(apiDetails());
    }
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Company",
                "A tech company that serve clients ",
                "1.0",
                null,
                new springfox.documentation.service.Contact("Bk_challenge", "https://bk.rw", "info@bk.rw"),
                "API License",
                "https://bk.rw",
                Collections.emptyList());
    }
}