package com.example.diploma.controller;

import com.example.diploma.entity.Image;
import com.example.diploma.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@Tag(name = "Изображения", description = "API для работы с картинками")
@CrossOrigin(value = "http://localhost:3000")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "Загрузить изображение")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        Image savedImage = imageService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedImage.getId());
    }

    @Operation(summary = "Получить изображение по ID")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(image.getData(), headers, HttpStatus.OK);
    }
}