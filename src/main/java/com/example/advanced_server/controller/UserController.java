package com.example.advanced_server.controller;

import java.util.UUID;

import com.example.advanced_server.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity getAllInfo() {
        return ResponseEntity.ok(userService.getAllUserInfo());
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getInfoById(id));
    }

    @GetMapping("/info")
    public ResponseEntity getUserInfo(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserInfo((authentication.getName())));
    }
}
