package com.example.advanced_server.service;

import com.example.advanced_server.dto.AuthDTO;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.RegisterUserDTO;

public interface AuthService {
    CustomSuccessResponse register(RegisterUserDTO user);
    CustomSuccessResponse login(AuthDTO user);
}
