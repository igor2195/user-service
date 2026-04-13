package ru.test_app.user_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * данные ответа на авторизацию
 */
@Data
@AllArgsConstructor
public class LoginResponseDto {
    String token;
}
