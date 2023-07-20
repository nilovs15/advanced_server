package com.example.advanced_server.logger;

import com.example.advanced_server.entity.LogEntity;
import com.example.advanced_server.repository.LogRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class LogHandleInterceptor implements HandlerInterceptor {

    private final LogRepository loggerRepository;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {

        loggerRepository.save(new LogEntity()
                .setMethod(request.getMethod())
                .setUrl(request.getRequestURI())
                .setStatus(String.valueOf(response.getStatus())));
    }
}
