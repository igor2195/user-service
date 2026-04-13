package ru.test_app.user_service.service;

import ru.test_app.user_service.model.AddressDto;

import java.util.List;

/**
 * Сервис для работы с Адресом
 */
public interface AddressService {

    /**
     * Поиск Адреса по Области/Городу/Улице
     *
     * @param search - параметр поиска
     * @return данные по адресу
     */
    List<AddressDto> findAll(String search);

    /**
     * Поиск адреса по id
     *
     * @param id идентификатор адреса
     * @return данные адреса
     */
    AddressDto findById(Long id);

    /**
     * Создать адрес
     *
     * @param addressDto данные создаваемого адреса
     * @return id созданного адреса
     */
    Long create(AddressDto addressDto);

    /**
     * Обновить Адрес
     *
     * @param id  идентификатор адреса
     * @param addressDto данные обновляемого адреса
     * @return обновленный адрес
     */
    AddressDto update(Long id, AddressDto addressDto);

    /**
     * Удалить Адрес
     *
     * @param id идентификатор адреса
     */
    void delete(Long id);
}
