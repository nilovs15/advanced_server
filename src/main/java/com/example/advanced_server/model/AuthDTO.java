package com.example.advanced_server.model;

import com.example.advanced_server.exception.ValidationConstants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthDTO {
    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @NotBlank(message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    private String email;
    @NotBlank(message = ValidationConstants.PASSWORD_NOT_VALID)
    private String password;
}
