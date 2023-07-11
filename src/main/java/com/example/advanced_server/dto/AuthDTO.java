package com.example.advanced_server.dto;

import com.example.advanced_server.exception.ValidationConstants;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthDTO {

    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @NotBlank(message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    private String email;

    @NotBlank(message = ValidationConstants.PASSWORD_NOT_VALID)
    private String password;
}
