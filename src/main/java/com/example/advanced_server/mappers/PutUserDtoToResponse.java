package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.PutUserDtoResponse;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PutUserDtoToResponse {
    PutUserDtoToResponse INSTANCE = Mappers.getMapper(PutUserDtoToResponse.class);
    PutUserDtoResponse putUserDtoToResponse(UserEntity entity);
}
