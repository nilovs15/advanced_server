package com.example.advanced_server.config;

import com.example.advanced_server.logger.LogHandleInterceptor;
import com.example.advanced_server.repository.LogRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@RequiredArgsConstructor
public class LogConfig extends WebMvcConfigurationSupport {

    private final LogRepository logRepository;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogHandleInterceptor(logRepository));
    }
}
