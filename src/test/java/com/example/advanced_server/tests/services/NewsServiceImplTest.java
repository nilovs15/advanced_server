package com.example.advanced_server.tests.services;

import com.example.advanced_server.dto.BaseSuccessResponse;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.newsDto.CreateNewsSuccessResponse;
import com.example.advanced_server.dto.newsDto.GetNewOutDto;
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

import static com.example.advanced_server.tests.services.TestsConstants.changedNewsDto;
import static com.example.advanced_server.tests.services.TestsConstants.incorrectChangedNewsDto;
import static com.example.advanced_server.tests.services.TestsConstants.news;
import static com.example.advanced_server.tests.services.TestsConstants.newsDto;
import static com.example.advanced_server.tests.services.TestsConstants.page;
import static com.example.advanced_server.tests.services.TestsConstants.perPage;
import static com.example.advanced_server.tests.services.TestsConstants.successStatusCode;
import static com.example.advanced_server.tests.services.TestsConstants.user;
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

    private GetNewOutDto getFirstNewsData(CustomSuccessResponse<PageableResponse> response) {
        return response.getData().getContent().get(0);
    }

    @Test
    void successCreateNews() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        CreateNewsSuccessResponse response = newsService.createNews(user.getId(), newsDto);

        assertTrue(response.isSuccess());
        assertEquals(successStatusCode, response.getStatusCode());

        verify(newsRepository, times(1)).save(any(NewsEntity.class));
    }

    @Test
    void successGetNews() {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        List<NewsEntity> allNews = List.of(news);
        Page<NewsEntity> newsInPage = new PageImpl<NewsEntity>(allNews, pageable, allNews.size());

        when(newsRepository.findAll(pageable)).thenReturn(newsInPage);

        CustomSuccessResponse<PageableResponse> response = newsService.getNews(page, perPage);

        assertTrue(response.isSuccess());
        assertEquals(successStatusCode, response.getStatusCode());
        assertNotNull(response.getData().getContent());
        assertNotNull(response.getData().getNumberOfElements());

        assertEquals(getFirstNewsData(response).getTitle(), news.getTitle());
        assertEquals(getFirstNewsData(response).getId(), news.getId());
        assertEquals(getFirstNewsData(response).getDescription(), news.getDescription());
        assertEquals(getFirstNewsData(response).getImage(), news.getImage());
        assertEquals(getFirstNewsData(response).getUsername(), news.getUsername());
        assertEquals(getFirstNewsData(response).getTags().get(0).getTitle(),
                news.getTags().get(0).getTitle());

        verify(newsRepository, times(1)).findAll(pageable);
    }

    @Test
    void successGetUserNews() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Pageable pageable = PageRequest.of(page - 1, perPage);
        List<NewsEntity> allNews = List.of(news);
        Page<NewsEntity> newsInPage = new PageImpl<NewsEntity>(allNews, pageable, allNews.size());

        when(newsRepository.findNewsByUserId(pageable, user.getId())).thenReturn(newsInPage);

        CustomSuccessResponse<PageableResponse> response = newsService.getUserNews(user.getId(), page, perPage);

        assertTrue(response.isSuccess());
        assertEquals(successStatusCode, response.getStatusCode());
        assertNotNull(response.getData().getContent());
        assertNotNull(response.getData().getNumberOfElements());

        assertEquals(getFirstNewsData(response).getTitle(), news.getTitle());
        assertEquals(getFirstNewsData(response).getId(), news.getId());
        assertEquals(getFirstNewsData(response).getDescription(), news.getDescription());
        assertEquals(getFirstNewsData(response).getImage(), news.getImage());
        assertEquals(getFirstNewsData(response).getUsername(), news.getUsername());
        assertEquals(getFirstNewsData(response).getTags().get(0).getTitle(),
                news.getTags().get(0).getTitle());

        verify(newsRepository, times(1)).findNewsByUserId(pageable, user.getId());
    }

    @Test
    void successFindNews() {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        List<NewsEntity> allNews = List.of(news);
        Page<NewsEntity> newsInPage = new PageImpl<NewsEntity>(allNews, pageable, allNews.size());

        when(newsRepository.findNewsByParam(
                user.getName(), "t", List.of(news.getTags().get(0).getTitle()), pageable))
                .thenReturn(newsInPage);

        CustomSuccessResponse<PageableResponse> response = newsService.findNews(
                user.getName(), "t", List.of(news.getTags().get(0).getTitle()), page, perPage);

        assertTrue(response.isSuccess());
        assertEquals(successStatusCode, response.getStatusCode());
        assertNotNull(response.getData().getContent());
        assertNotNull(response.getData().getNumberOfElements());

        assertEquals(getFirstNewsData(response).getTitle(), news.getTitle());
        assertEquals(getFirstNewsData(response).getId(), news.getId());
        assertEquals(getFirstNewsData(response).getDescription(), news.getDescription());
        assertEquals(getFirstNewsData(response).getImage(), news.getImage());
        assertEquals(getFirstNewsData(response).getUsername(), news.getUsername());
        assertEquals(getFirstNewsData(response).getTags().get(0).getTitle(),
                news.getTags().get(0).getTitle());

        verify(newsRepository, times(1))
                .findNewsByParam(anyString(), anyString(), anyList(), any());
    }

    @Test
    void successChangeNews() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));

        BaseSuccessResponse response = newsService.changeNews(news.getId(), changedNewsDto);

        assertTrue(response.isSuccess());
        assertEquals(successStatusCode, response.getStatusCode());

        verify(newsRepository, times(1)).findById(any());
        verify(newsRepository, times(1)).save(any(NewsEntity.class));
    }

    @Test
    void successDeleteNews() {
        when(newsRepository.findById(news.getId())).thenReturn(Optional.of(news));

        BaseSuccessResponse response = newsService.deleteNews(news.getId());

        assertTrue(response.isSuccess());
        assertEquals(successStatusCode, response.getStatusCode());

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
                () -> newsService.createNews(UUID.randomUUID(), newsDto));

        assertEquals(ValidationConstants.USER_NOT_FOUND, thrown.getMessage());
    }
}