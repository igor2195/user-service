package ru.test_app.user_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.test_app.user_service.model.Address;
import ru.test_app.user_service.repository.AddressRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    public List<Address> findAll(String search) {
        return (search != null && !search.isEmpty())
                ? addressRepository.findByCityContainingIgnoreCase(search)
                : addressRepository.findAll();
    }

    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow();
    }

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}
