package com.example.advanced_server.tests.services;

import java.util.List;
import java.util.UUID;

import com.example.advanced_server.dto.authDto.AuthDTO;
import com.example.advanced_server.dto.authDto.RegisterUserDTO;
import com.example.advanced_server.dto.newsDto.NewsDto;
import com.example.advanced_server.dto.usersDto.PutUserDto;
import com.example.advanced_server.entity.NewsEntity;
import com.example.advanced_server.entity.TagEntity;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.mappers.UserEntityMapper;

public interface TestsConstants {
    NewsDto newsDto = new NewsDto()
            .setDescription("text")
            .setImage("cats.png")
            .setTags(List.of("first"))
            .setTitle("Title");

    RegisterUserDTO registerUserDTO = new RegisterUserDTO()
            .setName("Bob")
            .setAvatar("Cat.png")
            .setPassword("P@SSW0RD")
            .setRole("USER")
            .setEmail("bob@gmail.com");

    UserEntity user = UserEntityMapper.INSTANCE.registerUserDtoToUserEntity(registerUserDTO)
            .setId(UUID.randomUUID());


    PutUserDto putUserDto = new PutUserDto()
            .setAvatar("putAva")
            .setName("putName")
            .setEmail("putEmail@put.com")
            .setRole("USER");


    PutUserDto incorrectPutUserDto = new PutUserDto()
            .setAvatar("putAva")
            .setName("putName")
            .setEmail("q")
            .setRole("USER");

    AuthDTO authDTO = new AuthDTO()
            .setEmail("bob@gmail.com")
            .setPassword("P@SSW0RD");

    AuthDTO incorrecrtAuthDTO = new AuthDTO()
            .setEmail("boB_001@gmail.com")
            .setPassword("P@SSW0RD_000");
    NewsEntity news = new NewsEntity()
            .setId(1L)
            .setDescription(newsDto.getDescription())
            .setImage(newsDto.getImage())
            .setTitle(newsDto.getTitle())
            .setUsername(user.getName())
            .setUser(user)
            .setTags(newsDto.getTags().stream()
                    .map(o -> new TagEntity().setTitle(o))
                    .toList());

    NewsDto changedNewsDto = new NewsDto()
            .setDescription("putDesc")
            .setTitle("putTitle")
            .setImage("putImage")
            .setTags(List.of("putTag1"));

    NewsDto incorrectChangedNewsDto = new NewsDto()
            .setDescription("")
            .setTitle("putTitle")
            .setImage("putImage")
            .setTags(List.of("putTag1"));
}
