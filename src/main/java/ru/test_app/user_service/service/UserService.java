package ru.test_app.user_service.service;

import ru.test_app.user_service.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll(String search);
    User findById(Long id);
    User save(User user);
    void delete(Long id);
}
