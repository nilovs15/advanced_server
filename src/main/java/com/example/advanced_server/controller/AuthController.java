package com.example.advanced_server.controller;

import javax.validation.Valid;

import com.example.advanced_server.dto.AuthDTO;
import com.example.advanced_server.dto.RegisterUserDTO;
import com.example.advanced_server.service.impl.AuthServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth/")
@Validated
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO registerUser) {
       return ResponseEntity.ok(authServiceImpl.register(registerUser));
    }

    @PostMapping("login")
    public ResponseEntity auth(@RequestBody @Valid AuthDTO authDTO) {
        return ResponseEntity.ok(authServiceImpl.login(authDTO));
    }
}
