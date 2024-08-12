package com.example.user.service;

import com.example.user.dto.request.UserDto;
import com.example.user.dto.response.PageResponse;
import com.example.user.dto.response.UserResponse;
import com.example.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User save(UserDto userDto);

    UserResponse findById(Long id);

    UserResponse findByUsername(String userName);

    PageResponse<?> findAll(int page, int size, String sortBy);

    void deleteById(Long id);

    PageResponse<?> getAllUserWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy);
}
