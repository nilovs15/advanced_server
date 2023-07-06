package com.example.advanced_server.repository;

import java.util.Optional;
import java.util.UUID;

import com.example.advanced_server.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
    UserEntity findUserByEmail(String email);
}

