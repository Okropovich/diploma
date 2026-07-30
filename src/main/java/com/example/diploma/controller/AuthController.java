package com.example.diploma.controller;

import com.example.diploma.dto.LoginReq;
import com.example.diploma.dto.LoginRes;
import com.example.diploma.dto.NewPasswordDto;
import com.example.diploma.dto.RegisterReq;
import com.example.diploma.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Аутентификация", description = "API для входа, регистрации и смены пароля")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Вход в систему")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешный вход"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })
    public ResponseEntity<LoginRes> login(@Valid @RequestBody LoginReq loginReq) {
        return ResponseEntity.ok(authService.login(loginReq));
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные")
    })
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterReq registerReq) {
        authService.register(registerReq);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/users/set_password")
    @Operation(summary = "Смена пароля")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пароль изменен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "401", description = "Неавторизован")
    })
    public ResponseEntity<Void> setPassword(@Valid @RequestBody NewPasswordDto newPasswordDto,
                                            Authentication authentication) {
        authService.changePassword(
                authentication.getName(),
                newPasswordDto.getCurrentPassword(),
                newPasswordDto.getNewPassword()
        );
        return ResponseEntity.ok().build();
    }
}