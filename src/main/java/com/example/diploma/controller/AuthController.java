package com.example.diploma.controller;

import com.example.diploma.dto.LoginReq;
import com.example.diploma.dto.LoginRes;
import com.example.diploma.dto.RegisterReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Аутентификация", description = "API для входа и регистрации")
public class AuthController {

    @PostMapping("/login")
    @Operation(summary = "Вход в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    public ResponseEntity<LoginRes> login(@Valid @RequestBody LoginReq loginReq) {
        LoginRes response = new LoginRes();
        response.setToken("mock-jwt-token");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterReq registerReq) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}