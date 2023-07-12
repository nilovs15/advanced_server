package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.RegisterUserDTO;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserEntity registerUserDtoToUserEntity(RegisterUserDTO registerUserDTO);
}
