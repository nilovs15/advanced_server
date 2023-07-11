package com.example.advanced_server.service.impl;

import java.util.List;
import java.util.UUID;

import com.example.advanced_server.dto.CustomSuccessResponse;
import com.example.advanced_server.dto.PublicUserView;
import com.example.advanced_server.entity.UserEntity;
import com.example.advanced_server.exception.CustomException;
import com.example.advanced_server.exception.ValidationConstants;
import com.example.advanced_server.mappers.PublicUserViewMapper;
import com.example.advanced_server.mappers.UserListViewMapper;
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
        List<UserEntity> userList = userRepository.findAll();
        List<PublicUserView> viewList = UserListViewMapper.INSTANCE.map(userList);
        return new CustomSuccessResponse<List<PublicUserView>>().setData(viewList).setStatusCode(1);
    }

    public CustomSuccessResponse<PublicUserView> getInfoById(UUID id) {
        if (userRepository.findById(id).isPresent()) {
            UserEntity user = userRepository.findById(id).orElseThrow(() ->
                    new CustomException(ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED));
            PublicUserView publicUserView = PublicUserViewMapper.INSTANCE.toDTO(user);
            return CustomSuccessResponse.getResponse(publicUserView);
        }
        else {
            throw new CustomException(ValidationConstants.MAX_UPLOAD_SIZE_EXCEEDED);
        }
    }

    public CustomSuccessResponse getUserInfo(String email) {
        UserEntity user = userRepository.findByEmail(email);
        PublicUserView publicUserView = PublicUserViewMapper.INSTANCE.toDTO(user);
        return new CustomSuccessResponse().setData(publicUserView).setStatusCode(1);
    }

    public boolean isEmailUnique(String email) {
        UserEntity existUser = userRepository.findByEmail(email);
        return existUser == null;
    }
}
