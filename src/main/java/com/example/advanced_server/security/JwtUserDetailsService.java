package com.example.advanced_server.security;

import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.UserRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String uuid) throws UsernameNotFoundException {
        Optional<UserEntity> person = userRepository.findById(UUID.fromString(uuid));
        if (person == null) {
            throw new UsernameNotFoundException(ValidationConstants.USER_NOT_FOUND);
        }
        JwtUser jwtUser = JwtUserFactory.create(person.get());
        return jwtUser;
    }
}
