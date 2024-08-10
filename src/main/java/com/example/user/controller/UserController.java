package com.example.user.controller;

import com.example.user.dto.request.UserDto;
import com.example.user.dto.response.ResponseData;
import com.example.user.dto.response.ResponseError;
import com.example.user.repository.UserRepository;
import com.example.user.service.AddressService;
import com.example.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.DeclareError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.HtmlEscapeTag;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final AddressService addressService;

    @GetMapping("/{id}")
    public ResponseData<?> getUserById(@PathVariable("id") @Valid long id) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "User with id " + id, userService.findById(id));

        } catch (Exception e) {
            return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }

    }


    @GetMapping("/getByUsername")
    public ResponseData<?> getUserByUsername(@RequestParam("name") @Valid String name) {
        try {
            return new ResponseData<>(HttpStatus.OK.value(), "User with name " + name, userService.findByUsername(name));
        }catch (Exception e){
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        }


    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0", required = false) int page,
                                         @Min(10) @RequestParam(defaultValue = "20", required = false) int size,
                                         @RequestParam String sortBy) {
        return ResponseEntity.ok().body(userService.findAll(page, size, sortBy));

    }


    @PostMapping
    public ResponseData<?> createUser(@Valid @RequestBody UserDto userDto) {
        try {
            userService.save(userDto);
            return new ResponseData<>(HttpStatus.CREATED.value(), "Create successful", userDto);
        } catch (Exception e) {
            log.error("errorMessage={}", e.getMessage(), e.getCause());
            return new ResponseError(HttpStatus.BAD_REQUEST.value(), "Add user fail");
        }

    }

    @DeleteMapping("{id}")
    public ResponseData<?> deleteUser(@PathVariable("id") @Valid long id) {
        try {
            userService.deleteById(id);
            addressService.deleteAddressByUserId(id);
            return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete id: " + id + " successful");
        }catch (Exception e){
            return new  ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        }
    }


}
