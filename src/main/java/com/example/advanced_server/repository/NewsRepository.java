package com.example.advanced_server.repository;

import java.util.List;
import java.util.Optional;
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

    @Query("SELECT n FROM NewsEntity n " +
            "JOIN n.tags t " +
            "WHERE (:author IS NULL OR n.username = :author) " +
            "AND (:keyword IS NULL " +
                "OR n.title LIKE %:keyword% " +
                "OR n.description LIKE %:keyword%) " +
            "AND (COALESCE(:tags) IS NULL OR t.title IN :tags)")
    Page<NewsEntity> findNewsByParam(
            @Param("author") String author,
            @Param("keyword") String keyword,
            @Param("tags") List<String> tags,
            Pageable pageable
    );

    @Query("SELECT n FROM NewsEntity n WHERE n.id = :news_id")
    Optional<NewsEntity> findById(@Param("news_id") Long id);
}
