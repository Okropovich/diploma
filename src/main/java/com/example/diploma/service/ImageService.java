package com.example.diploma.service;

import com.example.diploma.entity.Image;
import com.example.diploma.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;


    public Image uploadImage(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setData(file.getBytes());
        return imageRepository.save(image);
    }


    public Image saveImage(byte[] bytes) {
        Image image = new Image();
        image.setData(bytes);
        return imageRepository.save(image);
    }


    public Image getImage(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + id));
    }
}