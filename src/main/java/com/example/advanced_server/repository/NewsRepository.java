package com.example.advanced_server.repository;

import java.util.UUID;

import com.example.advanced_server.entity.NewsEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    @Query("SELECT n FROM NewsEntity n WHERE n.id = :user_id")
    Page<NewsEntity> findNewsByUserId(Pageable pageable, @Param("user_id") UUID id);
}
