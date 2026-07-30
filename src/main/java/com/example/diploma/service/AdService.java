package com.example.diploma.service;

import com.example.diploma.dto.AdDto;
import com.example.diploma.dto.AdsDto;
import com.example.diploma.dto.CreateAdDto;
import com.example.diploma.entity.Ad;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AdService {

    AdsDto getAllAds();

    AdDto createAd(CreateAdDto properties, MultipartFile image, Long userId) throws IOException;

    AdDto getAdById(Long id);

    Ad getAdEntityById(Long id);

    void deleteAd(Long id, Long userId, boolean isAdmin);

    AdDto updateAd(Long id, CreateAdDto properties, Long userId, boolean isAdmin);

    AdsDto getAdsByUser(Long userId);

    byte[] updateAdImage(Long id, MultipartFile image) throws IOException;
}