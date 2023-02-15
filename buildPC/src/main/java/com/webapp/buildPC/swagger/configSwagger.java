package com.webapp.buildPC.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class configSwagger {
    @Bean
    public Docket postsApi(){
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.webapp.buildPC.controller"))
                .build();
    }
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Building PC webapp")
                .description("Website for building and selling pc")
                .termsOfServiceUrl("")
                .licenseUrl("thinhknc@gmail.com").version("1.0").build();
    }
}
