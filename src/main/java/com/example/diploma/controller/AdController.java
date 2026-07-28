package com.example.diploma.controller;

import com.example.diploma.dto.AdDto;
import com.example.diploma.dto.AdsDto;
import com.example.diploma.dto.CreateAdDto;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.AdService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ads")
@RequiredArgsConstructor
public class AdController {

    private final AdService adService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<AdsDto> getAllAds() {
        return ResponseEntity.ok(adService.getAllAds());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDto> addAd(@RequestPart("properties") CreateAdDto properties,
                                       @RequestPart("image") MultipartFile image,
                                       Authentication authentication) throws IOException {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(adService.createAd(properties, image, userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdDto> getAds(@PathVariable Long id) {
        return ResponseEntity.ok(adService.getAdById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeAd(@PathVariable Long id, Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean isAdmin = checkIsAdmin(authentication);
        adService.deleteAd(id, userId, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AdDto> updateAds(@PathVariable Long id,
                                           @RequestBody CreateAdDto properties,
                                           Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean isAdmin = checkIsAdmin(authentication);
        return ResponseEntity.ok(adService.updateAd(id, properties, userId, isAdmin));
    }

    @GetMapping("/me")
    public ResponseEntity<AdsDto> getAdsMe(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        return ResponseEntity.ok(adService.getAdsByUser(userId));
    }

    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> updateImage(@PathVariable Long id,
                                              @RequestPart("image") MultipartFile image) throws IOException {
        byte[] imageData = adService.updateAdImage(id, image);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(imageData);
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email))
                .getId();
    }

    private boolean checkIsAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}