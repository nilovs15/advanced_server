package com.example.advanced_server.tests.services;

import com.example.advanced_server.dto.BaseSuccessResponse;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.usersDto.PublicUserView;
import com.example.advanced_server.dto.usersDto.PutUserDto;
import com.example.advanced_server.dto.usersDto.PutUserDtoResponse;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.example.advanced_server.tests.services.TestsConstants.*;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    Authentication authentication;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void getAllUserInfo_Should_Return_Correct_Result() {

        when(userRepository.findAll()).thenReturn(List.of(user));

        CustomSuccessResponse<List<PublicUserView>> response = userService.getAllUserInfo();

        assertTrue(response.isSuccess());
        assertEquals(response.getStatusCode(), 1);
        assertNotNull(response.getData().get(0).getId());
        assertNotNull(response.getData().get(0).getName());
        assertNotNull(response.getData().get(0).getRole());
        assertNotNull(response.getData().get(0).getEmail());
        assertNotNull(response.getData().get(0).getAvatar());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getInfoById_Should_Return_Correct_Result() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        CustomSuccessResponse<PublicUserView> response = userService.getInfoById(user.getId());

        assertTrue(response.isSuccess());
        assertEquals(response.getStatusCode(), 1);
        assertNotNull(response.getData().getId());
        assertNotNull(response.getData().getName());
        assertNotNull(response.getData().getRole());
        assertNotNull(response.getData().getEmail());
        assertNotNull(response.getData().getAvatar());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void should_ThrowException_When_Invalid_Id_In_GetInfoById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        try {
            CustomSuccessResponse<PublicUserView> response = userService.getInfoById(UUID.randomUUID());
        } catch (CustomException e) {
            assertEquals(e.getMessage(), ValidationConstants.USER_NOT_FOUND);
        }
    }

    @Test
    void getUserInfo_Should_Return_Correct_Result() {

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        CustomSuccessResponse<PublicUserView> response = userService.getUserInfo(user.getId());

        assertTrue(response.isSuccess());
        assertEquals(1, response.getStatusCode());
        assertNotNull(response.getData().getAvatar());
        assertNotNull(response.getData().getId());
        assertNotNull(response.getData().getEmail());
        assertNotNull(response.getData().getName());
        assertNotNull(response.getData().getRole());

        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void replaceUser_Should_Return_Correct_Result() throws IOException {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        FileServiceTest fileServiceTest = new FileServiceTest();
        fileServiceTest.success_UploadFile();

        CustomSuccessResponse<PutUserDtoResponse> response = userService.replaceUser(user.getId(), putUserDto);

        assertTrue(response.isSuccess());
        assertEquals(response.getStatusCode(), 1);
        assertNotNull(response.getData());

        verify(userRepository, times(1)).save(any(UserEntity.class));
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void test_When_Invalid_Email() {
        Set<ConstraintViolation<PutUserDto>> violations = validator.validate(incorrectPutUserDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    void deleteUser_Should_Return_Correct_Result() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        BaseSuccessResponse response = userService.deleteUser(user.getId());

        assertTrue(response.isSuccess());
        assertEquals(1, (int) response.getStatusCode());

        verify(userRepository, times(1)).deleteById(user.getId());
    }
}