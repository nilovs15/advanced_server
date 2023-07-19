package com.example.advanced_server.repository;

import com.example.advanced_server.entity.LogEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<LogEntity, Long> {

}
