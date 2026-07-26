package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Полная информация об объявлении")
public class FullAdDto {
    @Schema(description = "ID объявления")
    private Integer pk;
    @Schema(description = "Имя автора", example = "Иван")
    private String authorFirstName;
    @Schema(description = "Фамилия автора", example = "Иванов")
    private String authorLastName;
    @Schema(description = "Описание", example = "Велосипед в хорошем состоянии")
    private String description;
    @Schema(description = "URL изображения")
    private String image;
    @Schema(description = "Телефон", example = "+7(999)123-45-67")
    private String phone;
    @Schema(description = "Цена", example = "10000")
    private Integer price;
    @Schema(description = "Заголовок", example = "Продам велосипед")
    private String title;
}