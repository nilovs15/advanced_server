package com.example.advanced_server.repository;

import com.example.advanced_server.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<TagEntity, Long> {

}
