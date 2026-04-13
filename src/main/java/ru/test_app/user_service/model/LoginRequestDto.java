package ru.test_app.user_service.model;

import lombok.Data;

/**
 * Данные запроса для авторизации
 */
@Data
public class LoginRequestDto {
    String username;
    String password;
}
