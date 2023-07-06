package com.example.advanced_server.mappers;

import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.model.RegisterUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEntityMapper {
    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);
    UserEntity toDTO(RegisterUserDTO registerUserDTO);
}
