package org.example.exam.service;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.AuthResponseDTO;
import org.example.exam.dto.LoginRequestDTO;
import org.example.exam.dto.RegisterRequestDTO;
import org.example.exam.entity.User;
import org.example.exam.enums.Role;
import org.example.exam.repository.UserRepository;
import org.example.exam.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Bu email artıq istifadə olunur.");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setEnabled(true);

        userRepository.save(user);

    }


    public AuthResponseDTO login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email və ya şifrə yalnışdır."));

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token, user.getRole().name()); // Rolu da əlavə etdik
    }
}
