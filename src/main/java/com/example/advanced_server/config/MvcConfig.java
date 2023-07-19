package com.example.advanced_server.config;

import com.example.advanced_server.logger.LoggerHandleInterceptor;
import com.example.advanced_server.repository.LoggerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@RequiredArgsConstructor
public class MvcConfig extends WebMvcConfigurationSupport {

    private final LoggerRepository loggerRepository;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerHandleInterceptor(loggerRepository));
    }
}
