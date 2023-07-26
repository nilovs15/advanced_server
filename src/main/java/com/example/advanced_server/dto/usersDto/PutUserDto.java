package com.example.advanced_server.dto.usersDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.advanced_server.exception.ValidationConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class PutUserDto {
    private String avatar;

    @Size(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @NotNull(message = ValidationConstants.USER_EMAIL_NOT_NULL)
    private String email;

    @Size(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_NAME_HAS_TO_BE_PRESENT)
    private String name;

    @NotNull(message = ValidationConstants.USER_ROLE_NOT_NULL)
    @Size(min = 3, max = 25, message = ValidationConstants.ROLE_SIZE_NOT_VALID)
    private String role;
}
