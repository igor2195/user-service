package ru.test_app.user_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * данные ответа на авторизацию
 */
@Data
@AllArgsConstructor
@Schema(description = "JWT токен")
public class LoginResponseDto {

    @Schema(description = "токен", example = "eyJhbGciOiJIUzI1NiIs...")
    String token;
}
