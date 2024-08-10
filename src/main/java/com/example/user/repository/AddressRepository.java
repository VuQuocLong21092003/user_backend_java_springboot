package com.example.user.repository;

import com.example.user.model.Address;
import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    void deleteByUserId(Long userId);
}
