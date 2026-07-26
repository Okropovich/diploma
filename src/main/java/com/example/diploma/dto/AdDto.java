package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Краткая информация об объявлении")
public class AdDto {
    @Schema(description = "ID объявления")
    private Integer pk;
    @Schema(description = "ID автора")
    private Integer authorId;
    @Schema(description = "URL изображения")
    private String image;
    @Schema(description = "Цена", example = "10000")
    private Integer price;
    @Schema(description = "Заголовок", example = "Продам велосипед")
    private String title;
}