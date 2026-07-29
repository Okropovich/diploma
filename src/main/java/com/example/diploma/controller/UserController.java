package com.example.diploma.controller;

import com.example.diploma.dto.UpdateUserDto;
import com.example.diploma.dto.UserDto;
import com.example.diploma.entity.Image;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.ImageService;
import com.example.diploma.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageService imageService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getMe(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(userService.getMe(userId));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateMe(@RequestBody UpdateUserDto updateUserDto,
                                            Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        return ResponseEntity.ok(userService.updateMe(userId, updateUserDto));
    }

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUserImage(@RequestParam("image") MultipartFile image,
                                                Authentication authentication) throws IOException {
        Long userId = getUserIdFromAuthentication(authentication);
        Image savedImage = imageService.saveImage(image.getBytes());
        userService.updateAvatar(userId, savedImage.getId());
        return ResponseEntity.ok().build();
    }

    // Добавленный эндпоинт, который запрашивает фронтенд (устраняет ошибку 404)
    @GetMapping("/me/image/{id}")
    public ResponseEntity<byte[]> getUserImage(@PathVariable Long id) {
        Image image = imageService.getImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image.getData(), headers, org.springframework.http.HttpStatus.OK);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email))
                .getId();
    }
}