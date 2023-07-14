package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.PutUserDtoResponse;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PutUserDtoToResponse {
    PutUserDtoToResponse INSTANCE = Mappers.getMapper(PutUserDtoToResponse.class);

    @Mapping(target = "avatar", source = "avatar")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "role", source = "role")
    PutUserDtoResponse userEntityToPutUserDtoResponse(UserEntity entity);
}
