package org.example.exam.controller;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.AuthResponseDTO;
import org.example.exam.dto.LoginRequestDTO;
import org.example.exam.dto.RegisterRequestDTO;
import org.example.exam.entity.User;
import org.example.exam.enums.Role;
import org.example.exam.repository.UserRepository;
import org.example.exam.security.JwtUtil;
import org.example.exam.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        try {
            authService.register(request);
            return ResponseEntity.ok("İstifadəçi uğurla qeydiyyatdan keçdi.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Serverdə xəta baş verdi.");
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Email və ya şifrə yalnışdır.");
        }
    }
}
