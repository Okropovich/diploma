package com.example.diploma.service;

import com.example.diploma.dto.LoginReq;
import com.example.diploma.dto.LoginRes;
import com.example.diploma.dto.RegisterReq;
import com.example.diploma.entity.User;
import com.example.diploma.mapper.UserMapper;
import com.example.diploma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(RegisterReq registerReq) {
        User user = userMapper.toEntity(registerReq);
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        userRepository.save(user);
    }

    public LoginRes login(LoginReq loginReq) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword())
        );

        LoginRes response = new LoginRes();
        String credentials = loginReq.getUsername() + ":" + loginReq.getPassword();
        String token = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
        response.setToken(token);
        return response;
    }

    public void changePassword(String email, String oldPassword, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}