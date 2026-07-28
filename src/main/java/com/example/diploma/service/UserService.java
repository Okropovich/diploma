package com.example.diploma.service;

import com.example.diploma.dto.UserDto;
import com.example.diploma.dto.UpdateUserDto;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ImageService imageService;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public UserDto getMe(Long userId) {
        User user = findById(userId);
        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateMe(Long userId, UpdateUserDto updateUserDto) {
        User user = findById(userId);
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        user.setPhone(updateUserDto.getPhone());
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public void updateAvatar(Long userId, Long imageId) {
        User user = findById(userId);
        user.setImage(imageService.getImage(imageId));
        userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}