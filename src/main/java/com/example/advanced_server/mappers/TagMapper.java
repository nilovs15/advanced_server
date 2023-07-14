package com.example.advanced_server.mappers;

import com.example.advanced_server.dto.newsDto.Tag;
import com.example.advanced_server.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    Tag tagEntityToTagDto(TagEntity tagEntity);
}
