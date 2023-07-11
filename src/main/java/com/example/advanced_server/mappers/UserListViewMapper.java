package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.PublicUserView;
import com.example.advanced_server.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserListViewMapper {
    UserListViewMapper INSTANCE = Mappers.getMapper(UserListViewMapper.class);
    List<PublicUserView> map(List<UserEntity> entities);
}
