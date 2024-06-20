package com.startup.yourstar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Windows 파일 시스템 경로는 "file:///"로 시작해야 합니다.
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:/static/upload/");
    }
}
