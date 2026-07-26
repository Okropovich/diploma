package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Создание комментария")
public class CreateCommentDto {
    @Schema(description = "Текст комментария", example = "Отличное предложение!")
    private String text;
}