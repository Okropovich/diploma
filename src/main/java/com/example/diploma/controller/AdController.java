package com.example.diploma.controller;

import com.example.diploma.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "API для работы с объявлениями")
public class AdController {

    @GetMapping
    @Operation(summary = "Получить все объявления")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<AdsDto> getAllAds() {
        AdsDto response = new AdsDto();
        response.setCount(0);
        response.setResults(new ArrayList<>());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Создать объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Объявление создано"),
            @ApiResponse(responseCode = "401", description = "Не авторизован")
    })
    public ResponseEntity<AdDto> createAd(@Valid @RequestBody CreateAdDto createAdDto) {
        AdDto response = new AdDto();
        response.setPk(1);
        response.setPrice(createAdDto.getPrice());
        response.setTitle(createAdDto.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить объявление по ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    public ResponseEntity<FullAdDto> getAd(@PathVariable Integer id) {
        FullAdDto response = new FullAdDto();
        response.setPk(id);
        response.setTitle("Пример объявления");
        response.setDescription("Описание");
        response.setPrice(10000);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Объявление удалено"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    public ResponseEntity<Void> deleteAd(@PathVariable Integer id) {
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновить объявление")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Объявление обновлено"),
            @ApiResponse(responseCode = "403", description = "Доступ запрещен")
    })
    public ResponseEntity<AdDto> updateAd(@PathVariable Integer id,
                                          @Valid @RequestBody CreateAdDto createAdDto) {
        AdDto response = new AdDto();
        response.setPk(id);
        response.setPrice(createAdDto.getPrice());
        response.setTitle(createAdDto.getTitle());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/comments")
    @Operation(summary = "Получить комментарии к объявлению")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Объявление не найдено")
    })
    public ResponseEntity<CommentsDto> getComments(@PathVariable Integer id) {
        CommentsDto response = new CommentsDto();
        response.setCount(0);
        response.setResults(new ArrayList<>());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Создать комментарий")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий создан"),
            @ApiResponse(responseCode = "401", description = "Не авторизован")
    })
    public ResponseEntity<CommentDto> createComment(@PathVariable Integer id,
                                                    @Valid @RequestBody CreateCommentDto createCommentDto) {
        CommentDto response = new CommentDto();
        response.setPk(1);
        response.setText(createCommentDto.getText());
        return ResponseEntity.ok(response);
    }
}