package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Смена пароля")
public class NewPasswordDto {
    @Schema(description = "Текущий пароль", example = "oldPassword")
    private String currentPassword;
    @Schema(description = "Новый пароль", example = "newPassword")
    private String newPassword;
}