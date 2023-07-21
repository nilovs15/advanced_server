package com.example.advanced_server.tests.services;

import com.example.advanced_server.dto.BaseSuccessResponse;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.newsDto.CreateNewsSuccessResponse;
import com.example.advanced_server.dto.newsDto.NewsDto;
import com.example.advanced_server.dto.newsDto.PageableResponse;
import com.example.advanced_server.entity.NewsEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.NewsRepository;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.impl.NewsServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.example.advanced_server.tests.services.TestsConstants.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
class NewsServiceImplTest {

    @Autowired
    NewsServiceImpl newsService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    NewsRepository newsRepository;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void successCreateNews() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        CreateNewsSuccessResponse response = newsService.createNews(user.getId(), newsDto);

        assertTrue(response.isSuccess());
        assertTrue(response.getStatusCode().equals(1));

        verify(newsRepository, times(1)).save(any(NewsEntity.class));
    }

    @Test
    void successGetNews() {
        Pageable pageable = PageRequest.of(0, 3);
        List<NewsEntity> allNews = List.of(news);
        Page<NewsEntity> newsInPage = new PageImpl<NewsEntity>(allNews, pageable, allNews.size());

        when(newsRepository.findAll(pageable)).thenReturn(newsInPage);

        CustomSuccessResponse<PageableResponse> response = newsService.getNews(1, 3);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());
        assertNotNull(response.getData().getContent());
        assertNotNull(response.getData().getNumberOfElements());

        verify(newsRepository, times(1)).findAll(pageable);
    }

    @Test
    void successGetUserNews() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Pageable pageable = PageRequest.of(0, 3);
        List<NewsEntity> allNews = List.of(news);
        Page<NewsEntity> newsInPage = new PageImpl<NewsEntity>(allNews, pageable, allNews.size());

        when(newsRepository.findNewsByUserId(pageable, user.getId())).thenReturn(newsInPage);

        CustomSuccessResponse<PageableResponse> response = newsService.getUserNews(user.getId(), 1, 3);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());
        assertNotNull(response.getData().getContent());
        assertNotNull(response.getData().getNumberOfElements());

        verify(newsRepository, times(1)).findNewsByUserId(pageable, user.getId());
    }

    @Test
    void successFindNews() {
        Pageable pageable = PageRequest.of(0, 3);
        List<NewsEntity> allNews = List.of(news);
        Page<NewsEntity> newsInPage = new PageImpl<NewsEntity>(allNews, pageable, allNews.size());

        when(newsRepository.findNewsByParam(
                user.getName(), "t", List.of(news.getTags().get(0).getTitle()), pageable))
                .thenReturn(newsInPage);

        CustomSuccessResponse<PageableResponse> response = newsService.findNews(
                user.getName(), "t", List.of(news.getTags().get(0).getTitle()), 1, 3);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());
        assertNotNull(response.getData().getContent());
        assertNotNull(response.getData().getNumberOfElements());

        verify(newsRepository, times(1))
                .findNewsByParam(anyString(), anyString(), anyList(), any());
    }

    @Test
    void successChangeNews() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));

        BaseSuccessResponse response = newsService.changeNews(news.getId(), changedNewsDto);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());

        verify(newsRepository, times(1)).findById(any());
        verify(newsRepository, times(1)).save(any(NewsEntity.class));
    }

    @Test
    void successDeleteNews() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));

        BaseSuccessResponse response = newsService.deleteNews(news.getId());

        assertTrue(response.isSuccess());
        assertEquals(response.getStatusCode(), 1);

        verify(newsRepository, times(1)).deleteById(news.getId());
    }

    @Test
    void testWhenDescriptionIsNull() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));
        Set<ConstraintViolation<NewsDto>> violations = validator.validate(incorrectChangedNewsDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void shouldThrowException_WhenInvalidUserId() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        CustomException thrown = assertThrows(CustomException.class,
                () ->  newsService.createNews(UUID.randomUUID(), newsDto));

        assertEquals(ValidationConstants.USER_NOT_FOUND, thrown.getMessage());
    }
}