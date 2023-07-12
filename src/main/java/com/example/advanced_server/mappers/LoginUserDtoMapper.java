package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.LoginUserDto;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginUserDtoMapper {
    LoginUserDtoMapper INSTANCE = Mappers.getMapper(LoginUserDtoMapper.class);

    LoginUserDto userEntityToLoginUserDTO(UserEntity userEntity);
}
