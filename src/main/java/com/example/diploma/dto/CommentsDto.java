package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Список комментариев")
public class CommentsDto {
    @Schema(description = "Количество комментариев")
    private Integer count;
    @Schema(description = "Список комментариев")
    private List<CommentDto> results;
}