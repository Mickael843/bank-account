package com.mikkaeru.bankaccount.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path}")
    private String rootPath;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/" + rootPath + "/**")
                .allowedOrigins("*")
                .allowedMethods("POST,PUT,GET,DELETE,OPTIONS")
                .allowedHeaders("Location", "Content-type", "authorization")
                .exposedHeaders("Location", "Content-Disposition");
    }
}
