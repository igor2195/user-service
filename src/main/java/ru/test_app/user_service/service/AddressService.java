package ru.test_app.user_service.service;

import ru.test_app.user_service.model.AddressDto;

import java.util.List;

public interface AddressService {

    List<AddressDto> findAll(String search);

    AddressDto findById(Long id);

    Long create(AddressDto addressDto);

    Long update(Long id, AddressDto addressDto);

    void delete(Long id);
}
