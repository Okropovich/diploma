package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на логин")
public class LoginReq {
    @Schema(description = "Имя пользователя", example = "user@mail.ru")
    private String username;
    @Schema(description = "Пароль", example = "password")
    private String password;
}