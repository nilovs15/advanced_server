package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.PutUserDto;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PutUserDtoToEntityMapper {
    PutUserDtoToEntityMapper INSTANCE = Mappers.getMapper(PutUserDtoToEntityMapper.class);

    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "role", source = "role")
    UserEntity putUserDtoToUserEntity(PutUserDto putUserDto);
}
