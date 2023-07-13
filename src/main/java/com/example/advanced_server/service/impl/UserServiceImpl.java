package com.example.advanced_server.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.PublicUserView;
import com.example.advanced_server.dto.PutUserDto;
import com.example.advanced_server.dto.PutUserDtoResponse;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public CustomSuccessResponse<List<PublicUserView>> getAllUserInfo() {
        List<PublicUserView> userList = userRepository.findAll()
                .stream()
                .map(PublicUserViewMapper.INSTANCE::userEntityToPublicUserView)
                .toList();
        return  CustomSuccessResponse.getResponse(userList);
    }

    public CustomSuccessResponse<PublicUserView> getInfoById(UUID id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() ->
                new CustomException(ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED));
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
      UserEntity newUser = PutUserDtoToEntityMapper.INSTANCE.putUserDtoToEntity(putUserDto);
      newUser.setId(user.getId());
      newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
        PutUserDtoResponse putUserDtoResponse = PutUserDtoToResponse.INSTANCE.putUserDtoToResponse(newUser);
        return CustomSuccessResponse.getResponse(putUserDtoResponse);
    }
}
