package com.example.advanced_server.service;

import java.util.List;
import java.util.UUID;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.newsDto.CreateNewsSuccessResponse;
import com.example.advanced_server.dto.newsDto.NewsDto;


public interface NewsService {
    CreateNewsSuccessResponse createNews(UUID id, NewsDto newsDto);

    CustomSuccessResponse getNews(Integer page, Integer perPage);

    CustomSuccessResponse getUserNews(UUID id, Integer page, Integer perPage);

    CustomSuccessResponse findNews(String author, String keywords, List<String> tags, Integer page, Integer perPage);
}
