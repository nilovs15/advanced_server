package com.example.advanced_server.security;

import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public enum Roles implements GrantedAuthority {
    USER("user");

    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
