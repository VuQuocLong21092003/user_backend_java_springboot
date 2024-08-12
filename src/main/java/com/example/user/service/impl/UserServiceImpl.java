package com.example.user.service.impl;

import com.example.user.dto.request.AddressDto;
import com.example.user.dto.request.UserDto;
import com.example.user.dto.response.PageResponse;
import com.example.user.dto.response.UserResponse;
import com.example.user.exception.ResourceNotFoundException;
import com.example.user.model.Address;
import com.example.user.model.User;
import com.example.user.repository.SearchRepository;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SearchRepository searchRepository;
    private final UserRepository userRepository;

    @Override
    public User save(UserDto userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .phone(userDto.getPhone())
                .birthday(userDto.getBirthday())
                .username(userDto.getUsername())
                .gender(userDto.getGender())
                .status(userDto.getStatus())
                .userType(userDto.getUserType())
                .build();

        userDto.getAddress().forEach(a ->
                user.saveAddress(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())
                        .build()));

        return userRepository.save(user);

    }

    @Override
    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User with id " + id + " not found"));
        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        return userResponse;
    }

    @Override
    public UserResponse findByUsername(String userName) {
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(userName)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + userName + " not found")));


        User user = userOptional.get();

        UserResponse userResponse = UserResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();

        return userResponse;
    }

    @Override
    public PageResponse<?> findAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy));
        Page<User> users = userRepository.findAll(pageable);

        return converToPageResponse(users,pageable);
    }



    @Override
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User with id " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public PageResponse<?> getAllUserWithSortByColumnAndSearch(int pageNo, int pageSize, String search, String sortBy) {

        return searchRepository.getAllUserWithSortByColumnAndSearch(pageNo, pageSize, search, sortBy);
    }


    private Set<Address> convertToAddress(Set<AddressDto> addressDto) {
        Set<Address> result = new HashSet<>();

        addressDto.forEach(a ->
                result.add(Address.builder()
                        .apartmentNumber(a.getApartmentNumber())
                        .floor(a.getFloor())
                        .building(a.getBuilding())
                        .streetNumber(a.getStreetNumber())
                        .street(a.getStreet())
                        .city(a.getCity())
                        .country(a.getCountry())
                        .addressType(a.getAddressType())

                        .build())
        );
        return result;

    }

    private PageResponse<?> converToPageResponse(Page<User> users, Pageable pageable) {
        List<UserResponse> userList = users.stream().map(
                user -> UserResponse.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .phone(user.getPhone())
                        .username(user.getUsername())
                        .build()).toList();

        return PageResponse.builder()
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .total(users.getTotalPages())
                .items(userList)
                .build();
    }
}
