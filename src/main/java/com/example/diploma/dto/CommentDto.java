package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о комментарии")
public class CommentDto {
    @Schema(description = "ID комментария")
    private Integer pk;
    @Schema(description = "ID автора")
    private Integer authorId;
    @Schema(description = "Имя автора", example = "Иван")
    private String authorFirstName;
    @Schema(description = "Фамилия автора", example = "Иванов")
    private String authorLastName;
    @Schema(description = "URL аватара автора")
    private String authorImage;
    @Schema(description = "Дата создания")
    private Long createdAt;
    @Schema(description = "Текст комментария", example = "Отличное предложение!")
    private String text;
}