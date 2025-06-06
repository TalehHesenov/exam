package org.example.exam.controller;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.UserAnswerRequest;
import org.example.exam.service.UserAnswerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
@RequiredArgsConstructor
public class UserAnswerController {

    private final UserAnswerService userAnswerService;

    @PostMapping
    public ResponseEntity<?> submitAnswer(@RequestBody UserAnswerRequest request) {
        userAnswerService.saveUserAnswer(request);
        return ResponseEntity.ok().build();
    }

}
