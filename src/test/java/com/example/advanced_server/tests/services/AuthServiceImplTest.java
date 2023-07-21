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
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
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
    void successRegister() {

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
    void successLogin() {
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
    void shouldThrowException_WhenInvalidUserData() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        CustomException thrown = assertThrows(CustomException.class, () -> authService.login(incorrecrtAuthDTO));

        assertEquals(ValidationConstants.USER_NOT_FOUND, thrown.getMessage());
    }
}