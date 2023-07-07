package com.example.advanced_server.service;

import java.util.Optional;

import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.AuthRepository;
import com.example.advanced_server.security.PersonDetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PersonDetailsService implements UserDetailsService {
    private final AuthRepository authRepository;

    public PersonDetailsService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> person = authRepository.findByEmail(email);
        if (person.isEmpty()) {
            throw new UsernameNotFoundException(ValidationConstants.USER_NOT_FOUND);
        }
        return new PersonDetails(person.get());
    }
}
