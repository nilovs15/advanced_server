package com.example.advanced_server.repository;

import com.example.advanced_server.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    @Query("SELECT u FROM UserEntity u")
    List<UserEntity> findAll();
    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findById(@Param("id") UUID id);

    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    UserEntity findByEmail(@Param(value = "email") String email);
}

