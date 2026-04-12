package ru.test_app.user_service.service;

import ru.test_app.user_service.domain.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAll(String search);
    Address findById(Long id);
    Address save(Address address);
    void delete(Long id);
}
