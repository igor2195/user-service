package ru.test_app.user_service.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


/**
 * Модель запроса/ответа данных Пользоватлея
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Пользователь системы")
public class UserDto {

    @Schema(description = "ID пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя", example = "Иван")
    @NotBlank(message = "Имя обязательно")
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Отчество", example = "Иванович")
    private String middleName;

    @Schema(description = "Телефон", example = "+79161234567")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Номер должен состоять только из цифр и может начинаться с +")
    private String phone;

    @Schema(description = "Email", example = "test@mail.com")
    @Email(message = "Некорректный email")
    private String email;

    @Schema(description = "Дата рождения", example = "1990-05-15")
    @NotNull(message = "Дата рождения обязательна")
    private LocalDate birthDate;

    @Schema(description = "Адрес")
    private AddressDto address;
}
