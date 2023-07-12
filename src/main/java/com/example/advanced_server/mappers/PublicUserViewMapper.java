package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.PublicUserView;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublicUserViewMapper {
    PublicUserViewMapper INSTANCE = Mappers.getMapper(PublicUserViewMapper.class);

    PublicUserView userEntityToPublicUserView(UserEntity entity);
}
