package com.example.advanced_server.dto.usersDto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicUserView {
    private String avatar;
    private String email;
    private UUID id;
    private String name;
    private String role;
}