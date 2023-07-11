package com.example.advanced_server.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.advanced_server.exception.ValidationConstants;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDTO {

    @NotBlank(message = ValidationConstants.USER_AVATAR_NOT_NULL)
    private String avatar;
    @Size(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    private String email;

    @Size(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_NAME_HAS_TO_BE_PRESENT)
    private String name;

    private String password;

    @Size(min = 3, max = 25, message = ValidationConstants.ROLE_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_ROLE_NOT_NULL)
    private String role;
}

