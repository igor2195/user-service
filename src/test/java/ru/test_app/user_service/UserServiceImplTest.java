package ru.test_app.user_service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.test_app.user_service.domain.User;
import ru.test_app.user_service.model.UserDto;
import ru.test_app.user_service.repository.AddressRepository;
import ru.test_app.user_service.repository.UserRepository;
import ru.test_app.user_service.service.UserServiceImpl;
import ru.test_app.user_service.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void findAll_withoutSearch_returnsAllUsers() {
        List<User> users = List.of(new User(), new User());

        when(userRepository.findAllWithAddress()).thenReturn(users);
        when(userMapper.toDto(any())).thenReturn(new UserDto());

        List<UserDto> result = userService.findAll(null);

        assertEquals(2, result.size());
        verify(userRepository).findAllWithAddress();
    }

    @Test
    void findAll_withSearch_callsSearch() {
        List<User> users = List.of(new User());

        when(userRepository.search("ivan")).thenReturn(users);
        when(userMapper.toDto(any())).thenReturn(new UserDto());

        List<UserDto> result = userService.findAll("ivan");

        assertEquals(1, result.size());
        verify(userRepository).search("ivan");
    }

    @Test
    void findById_found() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(new UserDto());

        UserDto result = userService.findById(1L);

        assertNotNull(result);
    }

    @Test
    void findById_notFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.findById(1L));
    }

    @Test
    void create_userSaved() {
        UserDto dto = new UserDto();
        User user = new User();
        user.setId(10L);

        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        Long id = userService.create(dto);

        assertEquals(10L, id);
        verify(userRepository).save(user);
    }

    @Test
    void update_userUpdated() {
        User existing = new User();
        existing.setId(1L);

        UserDto dto = new UserDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));

        userService.update(1L, dto);

        verify(userMapper).update(existing, dto);
    }

    @Test
    void delete_userDeleted() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L);

        verify(userRepository).delete(user);
    }
}
