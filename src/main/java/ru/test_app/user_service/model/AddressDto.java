package ru.test_app.user_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Модель запроса/ответа данных Адреса
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    @NotBlank(message = "Область обязательна")
    private String region;
    @NotBlank(message = "Город обязателен")
    private String city;
    @NotBlank(message = "Улица обязательна")
    private String street;
    @NotBlank(message = "Дом обязателен")
    private String house;
    private String apartment;
}
