package com.mikkaeru.bankaccount.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev"})
public class SwaggerConfig {

    @Value("${release.version}")
    private String releaseVersion;

    @Value("${api.version}")
    private String apiVersion;

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mikkaeru.bankaccount.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(enableSwagger);
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Banco API")
                .description("Banco API - Documentação dos endpoints")
                .version(releaseVersion.concat("_").concat(apiVersion))
                .build();
    }
}
