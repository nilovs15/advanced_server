package com.example.advanced_server.controller;

import com.example.advanced_server.model.AuthDTO;
import com.example.advanced_server.model.RegisterUserDTO;
import com.example.advanced_server.service.AuthService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/auth/")
@Validated
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO registerUser) {
       return ResponseEntity.ok(authService.registration(registerUser));
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
        return ResponseEntity.ok(authService.login(authDTO));
    }
}