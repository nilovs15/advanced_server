package com.example.advanced_server.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.example.advanced_server.dto.BaseSuccessResponse;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.newsDto.CreateNewsSuccessResponse;
import com.example.advanced_server.dto.newsDto.GetNewOutDto;
import com.example.advanced_server.dto.newsDto.NewsDto;
import com.example.advanced_server.dto.newsDto.PageableResponse;
import com.example.advanced_server.entity.NewsEntity;
import com.example.advanced_server.entity.TagEntity;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.GetNewsDtoMapper;
import com.example.advanced_server.mappers.TagMapper;
import com.example.advanced_server.repository.NewsRepository;
import com.example.advanced_server.repository.TagRepository;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.NewsService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
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
        tagRepository.saveAll(tags);
        newsRepository.save(news);
        return CreateNewsSuccessResponse.getResponse(news.getId());
    }

    @Override
    public CustomSuccessResponse getNews(Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<NewsEntity> newsEntities = newsRepository.findAll(pageable);

        List<GetNewOutDto> getNewOutDtos = newsEntities.stream()
                .map(newsEntity -> GetNewsDtoMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId().toString())
                        .setTags(newsEntity.getTags().stream()
                                .map(TagMapper.INSTANCE::tagEntityToTagDto)
                                .toList()))
                .toList();

        return CustomSuccessResponse.getResponse(PageableResponse.getResponse(getNewOutDtos));
    }

    @Override
    public CustomSuccessResponse getUserNews(UUID id, Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<NewsEntity> newsEntities = newsRepository.findNewsByUserId(pageable, id);

        List<GetNewOutDto> getNewOutDtos = newsEntities.stream()
                .map(newsEntity -> GetNewsDtoMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId().toString())
                        .setTags(newsEntity.getTags().stream()
                                .map(TagMapper.INSTANCE::tagEntityToTagDto)
                                .toList()))
                .toList();

        return CustomSuccessResponse.getResponse(PageableResponse.getResponse(getNewOutDtos));
    }

    public CustomSuccessResponse findNews(String author, String keywords, List<String> tags, Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<NewsEntity> newsEntities = newsRepository.findNewsByParam(author, keywords, tags, pageable);

        List<GetNewOutDto> getNewOutDtos = newsEntities.stream()
                .map(newsEntity -> GetNewsDtoMapper.INSTANCE.newsEntityToGetNewsOutDto(newsEntity)
                        .setUserId(newsEntity.getUser().getId().toString())
                        .setTags(newsEntity.getTags().stream()
                                .map(TagMapper.INSTANCE::tagEntityToTagDto)
                                .toList()))
                .toList();

        return CustomSuccessResponse.getResponse(PageableResponse.getResponse(getNewOutDtos));
    }

    public BaseSuccessResponse changeNews(Long id, NewsDto newsDto) {
        NewsEntity news = newsRepository.findById(id)
                .orElseThrow(() -> new CustomException(ValidationConstants.NEWS_NOT_FOUND));
            news
                .setDescription(newsDto.getDescription())
                .setTitle(newsDto.getTitle())
                .setImage(newsDto.getImage())
                .setTags(newsDto.getTags().stream()
                        .map(tag -> new TagEntity()
                                .setTitle(tag))
                        .collect(Collectors.toList()));
        newsRepository.save(news);
        return BaseSuccessResponse.getResponse();
    }
}
