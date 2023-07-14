package com.example.advanced_server.repository;

import java.util.UUID;

import com.example.advanced_server.entity.NewsEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query(value = "SELECT * FROM news_entity WHERE user_id = :user_id", nativeQuery = true)
    Page<NewsEntity> findNewsByUser(Pageable pageable, @Param("user_id") UUID id);
}
