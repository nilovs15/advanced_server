package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.newsDto.GetNewOutDto;
import com.example.advanced_server.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GetNewsDtoMapper {
    GetNewsDtoMapper INSTANCE = Mappers.getMapper(GetNewsDtoMapper.class);

    @Mapping(target = "description", source = "description")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "username", source = "username")
    GetNewOutDto newsEntityToGetNewsOutDto(NewsEntity newsEntity);
}
