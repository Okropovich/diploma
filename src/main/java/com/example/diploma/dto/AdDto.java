package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Краткая информация об объявлении")
public class AdDto {
    @Schema(description = "ID объявления")
    private Long pk; // Было Integer

    @Schema(description = "ID автора")
    private Integer author;

    @Schema(description = "URL изображения")
    private String image;

    @Schema(description = "Цена", example = "10000")
    private Integer price;

    @Schema(description = "Заголовок", example = "Продам велосипед")
    private String title;

    @Schema(description = "Описание", example = "Описание товара")
    private String description;
}