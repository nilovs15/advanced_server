package com.example.advanced_server.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.example.advanced_server.dto.newsDto.NewsDto;
import com.example.advanced_server.service.NewsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/news")
@Validated
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity createNews(@RequestBody @Valid NewsDto newsDto, Authentication authentication) {
        return ResponseEntity.ok(newsService.createNews(UUID.fromString(authentication.getName()), newsDto));
    }
}
