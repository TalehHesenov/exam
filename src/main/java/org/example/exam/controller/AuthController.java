package org.example.exam.controller;


import lombok.RequiredArgsConstructor;
import org.example.exam.dto.LoginRequest;
import org.example.exam.dto.RegisterRequest;
import org.example.exam.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        String result = authService.register(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = authService.login(request);
            // Token-u JSON şəklində qaytarırıq
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            // Məsələn, AuthenticationException gəlsə, 401 qaytarırıq
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }
}


