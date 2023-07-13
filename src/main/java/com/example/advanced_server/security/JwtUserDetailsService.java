package com.example.advanced_server.security;

import java.util.UUID;

import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        UserEntity person = userRepository.findById(UUID.fromString(uuid)).orElseThrow(() ->
                new CustomException(ValidationConstants.USER_NOT_FOUND));
        JwtUser jwtUser = JwtUserFactory.create(person);
        return jwtUser;
    }
}
