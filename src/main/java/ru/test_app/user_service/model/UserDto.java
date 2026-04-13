package ru.test_app.user_service.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Имя обязательно")
    private String firstName;

    private String lastName;

    private String middleName;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Номер должен состоять только из цифр и может начинаться с +")
    private String phone;

    @Email(message = "Некорректный email")
    private String email;

    @NotNull(message = "Дата рождения обязательна")
    private LocalDate birthDate;

    private AddressDto address;
}
