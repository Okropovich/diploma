package com.example.diploma.service.impl;

import com.example.diploma.dto.AdDto;
import com.example.diploma.dto.AdsDto;
import com.example.diploma.dto.CreateAdDto;
import com.example.diploma.entity.Ad;
import com.example.diploma.entity.Image;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.AdMapper;
import com.example.diploma.repository.AdRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.AdService;
import com.example.diploma.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;
    private final AdMapper adMapper;

    @Override
    public AdsDto getAllAds() {
        List<Ad> ads = adRepository.findAll();
        return adMapper.toAdsDto(ads.size(), ads);
    }

    @Override
    @Transactional
    public AdDto createAd(CreateAdDto properties, MultipartFile image, Long userId) throws IOException {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        Image savedImage = imageService.saveImage(image.getBytes());

        Ad ad = new Ad();
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        ad.setAuthor(author);
        ad.setImage(savedImage);

        Ad savedAd = adRepository.save(ad);
        return adMapper.toAdDto(savedAd);
    }

    @Override
    public AdDto getAdById(Long id) {
        Ad ad = getAdEntityById(id);
        return adMapper.toAdDto(ad);
    }

    @Override
    public Ad getAdEntityById(Long id) {
        return adRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ad not found with id: " + id));
    }

    @Override
    @Transactional
    public void deleteAd(Long id, Long userId, boolean isAdmin) {
        Ad ad = getAdEntityById(id);
        checkOwnerOrAdmin(ad, userId, isAdmin);
        adRepository.delete(ad);
    }

    @Override
    @Transactional
    public AdDto updateAd(Long id, CreateAdDto properties, Long userId, boolean isAdmin) {
        Ad ad = getAdEntityById(id);
        checkOwnerOrAdmin(ad, userId, isAdmin);

        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());

        Ad updatedAd = adRepository.save(ad);
        return adMapper.toAdDto(updatedAd);
    }

    @Override
    public AdsDto getAdsByUser(Long userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        List<Ad> ads = adRepository.findByAuthor(author);
        return adMapper.toAdsDto(ads.size(), ads);
    }

    @Override
    @Transactional
    public byte[] updateAdImage(Long id, MultipartFile image) throws IOException {
        Ad ad = getAdEntityById(id);
        Image savedImage = imageService.saveImage(image.getBytes());
        ad.setImage(savedImage);
        adRepository.save(ad);
        return savedImage.getData();
    }

    private void checkOwnerOrAdmin(Ad ad, Long userId, boolean isAdmin) {
        if (!isAdmin && (ad.getAuthor() == null || !ad.getAuthor().getId().equals(userId))) {
            throw new AccessDeniedException("You don't have permission to modify this ad");
        }
    }
}