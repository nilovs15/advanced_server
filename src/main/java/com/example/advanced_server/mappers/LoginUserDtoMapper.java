package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.authDto.LoginUserDto;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginUserDtoMapper {
    LoginUserDtoMapper INSTANCE = Mappers.getMapper(LoginUserDtoMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "avatar", source = "avatar")
    LoginUserDto userEntityToLoginUserDTO(UserEntity userEntity);
}
