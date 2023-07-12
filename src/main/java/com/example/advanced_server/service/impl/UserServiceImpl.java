package com.example.advanced_server.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.PublicUserView;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.PublicUserViewMapper;
import com.example.advanced_server.repository.UserRepository;
import com.example.advanced_server.service.UserService;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        Optional<UserEntity> user = userRepository.findById(id);
        PublicUserView publicUserView = PublicUserViewMapper.INSTANCE.userEntityToPublicUserView(user.get());
        return CustomSuccessResponse.getResponse(publicUserView);
    }
}
