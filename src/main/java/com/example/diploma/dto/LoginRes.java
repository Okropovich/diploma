package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Ответ на логин")
public class LoginRes {
    @Schema(description = "Токен авторизации")
    private String token;
}