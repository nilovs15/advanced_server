package com.example.advanced_server.controller;

import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import com.example.advanced_server.dto.newsDto.NewsDto;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.service.NewsService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity getNews(
            @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @RequestParam Integer page,

            @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            @RequestParam Integer perPage) {
        return ResponseEntity.ok(newsService.getNews(page, perPage));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity getUserNews(Authentication authentication,

                                      @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
                                      @RequestParam Integer page,

                                      @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
                                      @RequestParam Integer perPage) {
        return ResponseEntity.ok(newsService.getUserNews(UUID.fromString(authentication.getName()), page, perPage));
    }
}