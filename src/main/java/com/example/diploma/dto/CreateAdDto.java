package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Создание объявления")
public class CreateAdDto {
    @Schema(description = "Заголовок", example = "Продам велосипед")
    private String title;
    @Schema(description = "Цена", example = "10000")
    private Integer price;
    @Schema(description = "Описание", example = "Велосипед в хорошем состоянии")
    private String description;
}