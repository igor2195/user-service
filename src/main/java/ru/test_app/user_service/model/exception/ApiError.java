package ru.test_app.user_service.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

/**
 * Модель базовой ошибки
 */
@Data
@AllArgsConstructor
public class ApiError {
    private String message;
    private Map<String, String> errors;
}
