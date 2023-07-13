package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.PutUserDto;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PutUserDtoToEntityMapper {
    PutUserDtoToEntityMapper INSTANCE = Mappers.getMapper(PutUserDtoToEntityMapper.class);

    UserEntity putUserDtoToUserEntity(PutUserDto putUserDto);
}
