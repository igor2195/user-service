package ru.test_app.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test_app.user_service.domain.Address;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.model.AddressDto;
import ru.test_app.user_service.repository.AddressRepository;
import ru.test_app.user_service.service.mapper.AddressMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private static final String ADDRESS_NOT_FOUND = "Address not found with id: %s";


    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Override
    public List<AddressDto> findAll(String search) {
        List<Address> list = (search != null && !search.isEmpty())
                ? addressRepository.search(search)
                : addressRepository.findAll();

        return list.stream()
                .map(addressMapper::toDto)
                .toList();
    }

    @Override
    public AddressDto findById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND.formatted(id)));
    }

    @Transactional
    @Override
    public Long create(AddressDto addressDto) {
        Address address = addressMapper.toEntity(addressDto);
        return addressRepository.save(address).getId();
    }

    @Transactional
    @Override
    public AddressDto update(Long id, AddressDto addressDto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND.formatted(id)));
        addressMapper.update(address, addressDto);
        return addressMapper.toDto(addressRepository.save(address));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND.formatted(id)));
        addressRepository.delete(address);
    }
}
