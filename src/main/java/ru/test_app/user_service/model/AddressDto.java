package ru.test_app.user_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Адрес пользователя")
public class AddressDto {

    @Schema(description = "ID адреса", example = "1")
    private Long id;

    @Schema(description = "Область", example = "Московская область")
    @NotBlank(message = "Область обязательна")
    private String region;

    @Schema(description = "Город", example = "Москва")
    @NotBlank(message = "Город обязателен")
    private String city;

    @Schema(description = "Улица", example = "Тверская")
    @NotBlank(message = "Улица обязательна")
    private String street;

    @Schema(description = "Дом", example = "10")
    @NotBlank(message = "Дом обязателен")
    private String house;

    @Schema(description = "Квартира", example = "15")
    private String apartment;
}
