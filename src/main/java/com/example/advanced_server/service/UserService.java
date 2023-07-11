package com.example.advanced_server.service;

import java.util.UUID;

import com.example.advanced_server.dto.CustomSuccessResponse;

public interface UserService {
    CustomSuccessResponse getAllUserInfo();
    CustomSuccessResponse getInfoById(UUID id);
    CustomSuccessResponse getUserInfo(String email);
}
