package ru.test_app.user_service.model.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Модель базовой ошибки
 */
@Data
@AllArgsConstructor
@Schema(name = "ApiError", description = "Стандартная ошибка API")
public class ApiError {
    @Schema(description = "Сообщение об ошибке", example = "Validation failed")
    private String message;
    @Schema(
            description = "Список ошибок по полям",
            example = "{\"email\":\"Некорректный email\",\"phone\":\"Неверный формат\"}"
    )
    private Map<String, String> errors;
}
