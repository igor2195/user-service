package ru.test_app.user_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Данные запроса для авторизации
 */
@Schema(description = "Запрос на логин")
@Data
public class LoginRequestDto {

    @Schema(description = "Логин", example = "admin")
    String username;

    @Schema(description = "Пароль", example = "password")
    String password;
}
