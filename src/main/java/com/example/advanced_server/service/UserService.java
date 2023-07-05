package com.example.advanced_server.service;

import com.example.advanced_server.config.JWTUtil;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.RegisterMapper;
import com.example.advanced_server.mappers.UserMapper;
import com.example.advanced_server.model.CustomSuccessResponse;
import com.example.advanced_server.model.LoginUserDto;
import com.example.advanced_server.model.RegisterUserDTO;
import com.example.advanced_server.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public CustomSuccessResponse<LoginUserDto> registration(RegisterUserDTO registerUser) {
        if (!isEmailUnique(registerUser.getEmail())) {
            throw new IllegalArgumentException(ValidationConstants.USER_WITH_THIS_EMAIL_ALREADY_EXIST);
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
}
