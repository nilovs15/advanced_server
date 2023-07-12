package com.example.advanced_server.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class LoginUserDto {
    private String avatar;
    private String email;
    private UUID id;
    private String name;
    private String role;
    private String token;
}
