package com.example.advanced_server.tests.services;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.authDto.LoginUserDto;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.security.JwtTokenProvider;
import com.example.advanced_server.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static com.example.advanced_server.tests.services.TestsConstants.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthServiceImplTest {

    @Autowired
    private AuthServiceImpl authService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    void register_Should_Return_Correct_Result() {

        when(jwtTokenProvider.createToken(anyString())).thenReturn("Bearer_");
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        CustomSuccessResponse<LoginUserDto> response = authService.register(registerUserDTO);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());

        assertNotNull(response.getData().getId());
        assertNotNull(response.getData().getAvatar());
        assertNotNull(response.getData().getEmail());
        assertNotNull(response.getData().getName());
        assertNotNull(response.getData().getToken());

        verify(userRepository, times(1)).save(ArgumentMatchers.any(UserEntity.class));
        verify(userRepository, times(1)).findByEmail(registerUserDTO.getEmail());
    }

    @Test
    void login_Should_Return_Correct_Result() {
        when(jwtTokenProvider.createToken(anyString())).thenReturn("Bearer_");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));

        CustomSuccessResponse<LoginUserDto> response = authService.login(authDTO);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());

        assertNotNull(response.getData().getAvatar());
        assertNotNull(response.getData().getEmail());
        assertNotNull(response.getData().getId());
        assertNotNull(response.getData().getName());
        assertNotNull(response.getData().getRole());
        assertNotNull(response.getData().getToken());

        verify(userRepository, times(1)).findByEmail(authDTO.getEmail());
    }

    @Test
    void should_ThrowException_When_Invalid_User_Data() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(user));
        try {
            CustomSuccessResponse<LoginUserDto> response = authService.login(incorrecrtAuthDTO);
        } catch (CustomException e) {
            assertEquals(ValidationConstants.USER_NOT_FOUND, e.getMessage());
        }
    }
}