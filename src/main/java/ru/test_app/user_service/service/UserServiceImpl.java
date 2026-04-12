package ru.test_app.user_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<User> findAll(String search) {
        return (search != null && !search.isEmpty())
                ? userRepository.findByFirstNameContainingIgnoreCase(search)
                : userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
