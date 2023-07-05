package com.example.advanced_server.mappers;


import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.model.RegisterUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RegisterMapper {
    RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);
    UserEntity toDTO(RegisterUserDTO registerUserDTO);
}
