package ru.test_app.user_service.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.test_app.user_service.domain.Address;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.model.UserDto;
import ru.test_app.user_service.repository.AddressRepository;
import ru.test_app.user_service.repository.UserRepository;
import ru.test_app.user_service.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "User not found with id: %s";
    private static final String ADDRESS_NOT_FOUND = "Address not found with id: %s";

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> findAll(String search) {
        List<User> list = (search != null && !search.isEmpty())
                ? userRepository.search(search)
                : userRepository.findAllWithAddress();

        return list.stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.formatted(id)));
    }

    @Transactional
    @Override
    public Long create(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        setAddress(user, extractAddressId(userDto));
        return userRepository.save(user).getId();
    }

    @Transactional
    @Override
    public Long update(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.formatted(id)));
        userMapper.update(user, userDto);
        setAddress(user, extractAddressId(userDto));
        return userRepository.save(user).getId();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND.formatted(id)));
        userRepository.delete(user);
    }

    private Long extractAddressId(UserDto dto) {
        return dto.getAddress() != null
                ? dto.getAddress().getId()
                : null;
    }

    private void setAddress(User user, Long addressId) {
        Address address = Optional.ofNullable(addressId)
                .map(id -> addressRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException(ADDRESS_NOT_FOUND.formatted(addressId))))
                .orElse(null);
        user.setAddress(address);
    }
}
