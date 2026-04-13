package ru.test_app.user_service.service;

import ru.test_app.user_service.model.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll(String search);

    UserDto findById(Long id);

    Long create(UserDto userDto);

    Long update(Long id, UserDto userDto);

    void delete(Long id);
}
