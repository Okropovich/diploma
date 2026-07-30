package com.example.diploma.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Список объявлений")
public class AdsDto {
    @Schema(description = "Количество объявлений")
    private Integer count;
    @Schema(description = "Список объявлений")
    private List<AdDto> results;
}