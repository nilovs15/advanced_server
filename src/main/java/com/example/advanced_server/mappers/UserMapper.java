package com.example.advanced_server.mappers;


import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.model.LoginUserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    LoginUserDto toDTO(UserEntity userEntity);
}
