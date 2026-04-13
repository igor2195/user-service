package ru.test_app.user_service.service;

import ru.test_app.user_service.model.UserDto;

import java.util.List;

/**
 * Сервис для работы с Пользователем
 */
public interface UserService {

    /**
     * Поиск Пользоватлея по Области/Городу/Улице
     *
     * @param search - параметр поиска
     * @return данные по пользователю
     */
    List<UserDto> findAll(String search);

    /**
     * Поиск пользователя по id
     *
     * @param id идентификатор пользователя
     * @return данные пользователя
     */
    UserDto findById(Long id);

    /**
     * Создать пользователя
     *
     * @param userDto данные создаваемого пользователя
     * @return id созданного пользователя
     */
    Long create(UserDto userDto);

    /**
     * Обновить Пользователя
     *
     * @param id  идентификатор пользователя
     * @param userDto данные обновляемого пользователя
     * @return обновленный пользователь
     */
    UserDto update(Long id, UserDto userDto);

    /**
     * Удалить Адрес
     *
     * @param id идентификатор адреса
     */
    void delete(Long id);
}
