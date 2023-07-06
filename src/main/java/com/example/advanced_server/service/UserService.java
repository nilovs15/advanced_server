package com.example.advanced_server.service;

import java.util.Optional;
import java.util.UUID;

import com.example.advanced_server.config.JWTUtil;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.RegisterMapper;
import com.example.advanced_server.mappers.UserMapper;
import com.example.advanced_server.model.AuthDTO;
import com.example.advanced_server.model.CustomSuccessResponse;
import com.example.advanced_server.model.LoginUserDto;
import com.example.advanced_server.model.RegisterUserDTO;
import com.example.advanced_server.repository.UserRepository;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JWTUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public CustomSuccessResponse<LoginUserDto> registration(RegisterUserDTO registerUser) {
        if (!isEmailUnique(registerUser.getEmail())) {
            throw new IllegalArgumentException(ValidationConstants.USER_ALREADY_EXISTS);
        }
            UserEntity userEntity = RegisterMapper.INSTANCE.toDTO(registerUser);
            userEntity.setPassword(passwordEncoder.encode(registerUser.getPassword()));

            userRepository.save(userEntity);

            LoginUserDto loginUserDto = UserMapper.INSTANCE.toDTO(userEntity);
            loginUserDto.setId(UUID.fromString(userEntity.getId().toString()));
            loginUserDto.setToken(jwtUtil.generateToken(userEntity.getEmail()));
            return new CustomSuccessResponse<LoginUserDto>().setData(loginUserDto).setStatusCode(1);
    }

    public boolean isEmailUnique(String email) {
        Optional<UserEntity> existUser = userRepository.findByEmail(email);
        return existUser.isEmpty();
    }

    public CustomSuccessResponse<LoginUserDto> login(AuthDTO authDTO) {
        String email = authDTO.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authDTO.getPassword()));
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        String token = jwtUtil.generateToken(email);
        LoginUserDto loginUserDto = UserMapper.INSTANCE.toDTO(userEntity.get());
        loginUserDto.setId(UUID.fromString(userEntity.get().getId().toString()));
        loginUserDto.setToken(token);
        return new CustomSuccessResponse<LoginUserDto>().setData(loginUserDto).setStatusCode(1);
    }
}
