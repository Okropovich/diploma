package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о пользователе")
public class UserDto {
    @Schema(description = "ID пользователя")
    private Integer id;
    @Schema(description = "Имя пользователя", example = "user@mail.ru")
    private String email;
    @Schema(description = "Имя", example = "Иван")
    private String firstName;
    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;
    @Schema(description = "Телефон", example = "+7(999)123-45-67")
    private String phone;
    @Schema(description = "URL аватара")
    private String image;
}