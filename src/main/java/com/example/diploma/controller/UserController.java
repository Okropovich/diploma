package com.example.diploma.controller;

import com.example.diploma.dto.UpdateUserDto;
import com.example.diploma.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Пользователи", description = "API для работы с пользователями")
public class UserController {

    @GetMapping("/me")
    @Operation(summary = "Получить информацию о текущем пользователе")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<UserDto> getMe() {
        UserDto response = new UserDto();
        response.setId(1);
        response.setEmail("user@mail.ru");
        response.setFirstName("Иван");
        response.setLastName("Иванов");
        response.setPhone("+7(999)123-45-67");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me")
    @Operation(summary = "Обновить информацию о пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<UserDto> updateMe(@Valid @RequestBody UpdateUserDto updateUserDto) {
        UserDto response = new UserDto();
        response.setId(1);
        response.setEmail("user@mail.ru");
        response.setFirstName(updateUserDto.getFirstName());
        response.setLastName(updateUserDto.getLastName());
        response.setPhone(updateUserDto.getPhone());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me/image")
    @Operation(summary = "Загрузить аватар")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки")
    })
    public ResponseEntity<Void> uploadImage() {
        return ResponseEntity.ok().build();
    }
}