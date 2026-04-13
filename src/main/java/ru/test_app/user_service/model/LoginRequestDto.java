package ru.test_app.user_service.model;

import lombok.Data;

@Data
public class LoginRequestDto {
    String username;
    String password;
}
