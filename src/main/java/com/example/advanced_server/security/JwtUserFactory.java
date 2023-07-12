package com.example.advanced_server.security;

import com.example.advanced_server.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class JwtUserFactory {
    public static JwtUser create(UserEntity user) {
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getAvatar(),
                mapToGrantedAuthorities(Collections.singletonList(Roles.USER.getAuthority())));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Collection roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .toList();
    }
}
