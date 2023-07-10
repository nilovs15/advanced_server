package com.example.advanced_server.service;

import java.util.Optional;
import java.util.UUID;

import com.example.advanced_server.config.JwtTokenProvider;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.UserEntityMapper;
import com.example.advanced_server.mappers.LoginUserDtoMapper;
import com.example.advanced_server.model.AuthDTO;
import com.example.advanced_server.model.CustomSuccessResponse;
import com.example.advanced_server.model.LoginUserDto;
import com.example.advanced_server.model.RegisterUserDTO;
import com.example.advanced_server.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public CustomSuccessResponse<LoginUserDto> registration(RegisterUserDTO registerUser) {
        if (!isEmailUnique(registerUser.getEmail())) {
            throw new CustomException(ValidationConstants.USER_ALREADY_EXISTS);
        }
        UserEntity userEntity = UserEntityMapper.INSTANCE.registerUserDtoToUserEntity(registerUser);
        userEntity.setPassword(passwordEncoder.encode(registerUser.getPassword()));

        userRepository.save(userEntity);

        LoginUserDto loginUserDto = LoginUserDtoMapper.INSTANCE.userEntityToLoginUserDTO(userEntity);
        loginUserDto.setId(UUID.fromString(userEntity.getId().toString()));
        loginUserDto.setToken(jwtTokenProvider.generateToken(userEntity.getEmail()));
        return CustomSuccessResponse.getResponse(loginUserDto);
    }

    public boolean isEmailUnique(String email) {
        Optional<UserEntity> existUser = userRepository.findByEmail(email);
        return existUser.isEmpty();
    }

    public CustomSuccessResponse<LoginUserDto> login(AuthDTO authDTO) {
        String email = authDTO.getEmail();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, authDTO.getPassword()));
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);

        String token = jwtTokenProvider.generateToken(email);
        LoginUserDto loginUserDto = LoginUserDtoMapper.INSTANCE.userEntityToLoginUserDTO(userEntity.get());
        loginUserDto.setId(UUID.fromString(userEntity.get().getId().toString()));
        loginUserDto.setToken(token);
        return CustomSuccessResponse.getResponse(loginUserDto);
    }
}