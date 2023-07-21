package com.example.advanced_server.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.advanced_server.dto.BaseSuccessResponse;
import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.usersDto.PublicUserView;
import com.example.advanced_server.dto.usersDto.PutUserDto;
import com.example.advanced_server.dto.usersDto.PutUserDtoResponse;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.PublicUserViewMapper;
import com.example.advanced_server.mappers.PutUserDtoToEntityMapper;
import com.example.advanced_server.mappers.PutUserDtoToResponse;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public CustomSuccessResponse<List<PublicUserView>> getAllUserInfo() {
        List<PublicUserView> userList = userRepository.findAll()
                .stream()
                .map(PublicUserViewMapper.INSTANCE::userEntityToPublicUserView)
                .toList();
        return CustomSuccessResponse.getResponse(userList);
    }

    public CustomSuccessResponse<PublicUserView> getInfoById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new CustomException(ValidationConstants.USER_NOT_FOUND));
        PublicUserView publicUserView = PublicUserViewMapper.INSTANCE.userEntityToPublicUserView(user);
        return CustomSuccessResponse.getResponse(publicUserView);
    }

    public CustomSuccessResponse getUserInfo(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new CustomException(ValidationConstants.USER_NOT_FOUND));
        PublicUserView publicUserView = PublicUserViewMapper.INSTANCE.userEntityToPublicUserView(user);
        return CustomSuccessResponse.getResponse(publicUserView);
    }

    @Override
    public CustomSuccessResponse replaceUser(UUID id, PutUserDto putUserDto) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new CustomException(ValidationConstants.USER_NOT_FOUND));
        if (!isEmailUnique(putUserDto.getEmail())) {
            throw new CustomException(ValidationConstants.USER_ALREADY_EXISTS);
        }
        UserEntity newUser = PutUserDtoToEntityMapper.INSTANCE.putUserDtoToUserEntity(putUserDto)
                    .setAvatar(user.getAvatar())
                    .setId(user.getId())
                    .setPassword(user.getPassword());

        userRepository.save(newUser);
        PutUserDtoResponse putUserDtoResponse = PutUserDtoToResponse.INSTANCE.userEntityToPutUserDtoResponse(newUser);
        return CustomSuccessResponse.getResponse(putUserDtoResponse);
    }

    public boolean isEmailUnique(String email) {
        Optional<UserEntity> existUser = userRepository.findByEmail(email);
        return existUser.isEmpty();
    }

    @Transactional
    public BaseSuccessResponse deleteUser(UUID id) {
        userRepository.deleteById(id);
        return BaseSuccessResponse.getResponse();
    }
}
