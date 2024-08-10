package com.example.user.service.impl;

import com.example.user.exception.ResourceNotFoundException;
import com.example.user.repository.AddressRepository;
import com.example.user.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;


    @Override
    public void deleteAddressByUserId(long id) {
        addressRepository.deleteByUserId(id);
    }
}
