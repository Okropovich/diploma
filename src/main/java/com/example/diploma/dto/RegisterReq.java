package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class RegisterReq {
    @Schema(description = "Имя пользователя", example = "user@mail.ru")
    private String username;
    @Schema(description = "Пароль", example = "password")
    private String password;
    @Schema(description = "Имя", example = "Иван")
    private String firstName;
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;
    @Schema(description = "Телефон", example = "+7(999)123-45-67")
    private String phone;
    @Schema(description = "Роль", example = "USER")
    private String role;
}