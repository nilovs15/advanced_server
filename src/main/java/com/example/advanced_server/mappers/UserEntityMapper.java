package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.authDto.RegisterUserDTO;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    UserEntity registerUserDtoToUserEntity(RegisterUserDTO registerUserDTO);
}
