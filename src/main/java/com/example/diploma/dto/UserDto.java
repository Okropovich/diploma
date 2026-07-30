package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о пользователе")
public class UserDto {
    @Schema(description = "ID пользователя")
    private Integer id;
    @Schema(description = "email (логин)")
    private String email;
    @Schema(description = "Имя")
    private String firstName;
    @Schema(description = "Фамилия")
    private String lastName;
    @Schema(description = "Телефон")
    private String phone;
    @Schema(description = "Ссылка на аватар")
    private String image;
    @Schema(description = "Роль пользователя", example = "ADMIN")
    private String role;
}