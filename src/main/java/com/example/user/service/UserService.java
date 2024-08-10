package com.example.user.service;

import com.example.user.dto.request.UserDto;
import com.example.user.dto.response.UserResponse;
import com.example.user.model.User;

import java.util.List;

public interface UserService {
    User save(UserDto userDto);

    UserResponse findById(Long id);

    UserResponse findByUsername(String userName);

    List<UserResponse> findAll(int page, int size, String sortBy);

    void deleteById(Long id);
}
