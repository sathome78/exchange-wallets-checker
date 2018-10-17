package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "172.31.3.72")
                .allowedMethods(HttpMethod.GET.toString(), HttpMethod.PUT.toString())
                .allowedHeaders("Access-Control-Allow-Origin")
                .exposedHeaders("header1", "header2").
                maxAge(3600);
    }

}
