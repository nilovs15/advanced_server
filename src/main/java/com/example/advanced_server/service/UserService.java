package com.example.advanced_server.service;

import java.util.UUID;

import com.example.advanced_server.dto.BaseSuccessResponse;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.PutUserDto;

public interface UserService {
    CustomSuccessResponse getAllUserInfo();
    CustomSuccessResponse getInfoById(UUID id);
    CustomSuccessResponse getUserInfo(UUID id);
    CustomSuccessResponse replaceUser(UUID id, PutUserDto putUserDto);
    BaseSuccessResponse deleteUser(UUID id);
}
