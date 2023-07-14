package com.example.advanced_server.service.impl;

import java.util.List;
import java.util.UUID;

import com.example.advanced_server.dto.newsDto.CreateNewsSuccessResponse;
import com.example.advanced_server.dto.newsDto.NewsDto;
import com.example.advanced_server.entity.NewsEntity;
import com.example.advanced_server.entity.TagEntity;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.NewsRepository;
import com.example.advanced_server.repository.TagsRepository;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.NewsService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final TagsRepository tagsRepository;
    private final UserRepository userRepository;

    @Override
    public CreateNewsSuccessResponse createNews(UUID id, NewsDto newsDto) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ValidationConstants.USER_NOT_FOUND));
        List<TagEntity> tags = newsDto.getTags()
                .stream()
                .map(t -> new TagEntity().setTitle(t))
                .toList();
        NewsEntity news = new NewsEntity()
                .setDescription(newsDto.getDescription())
                .setImage(newsDto.getImage())
                .setTitle(newsDto.getTitle())
                .setUsername(user.getName())
                .setTags(tags)
                .setUser(user);
        tagsRepository.saveAll(tags);
        newsRepository.save(news);
        return CreateNewsSuccessResponse.getResponse(news.getId());
    }
}
