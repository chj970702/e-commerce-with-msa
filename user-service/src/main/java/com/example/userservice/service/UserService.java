package com.example.userservice.service;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.vo.UserResponse;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserResponse getUser(String userId);

    List<UserResponse> getUsers();
}
