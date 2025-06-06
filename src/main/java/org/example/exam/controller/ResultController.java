package org.example.exam.controller;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.ResultResponse;
import org.example.exam.entity.Question;
import org.example.exam.entity.User;
import org.example.exam.entity.UserAnswer;
import org.example.exam.repository.UserAnswerRepository;
import org.example.exam.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/result")
@RequiredArgsConstructor
public class ResultController {

    private final UserAnswerRepository userAnswerRepository;
    private final UserRepository userRepository;

    @GetMapping("/calculate/{examId}")
    public ResponseEntity<ResultResponse> calculateResult(
            @PathVariable Long examId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        // İstifadəçini email (username) ilə tapırıq
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Cavabları istifadəçi id və imtahan id-yə görə çəkirik
        List<UserAnswer> answers = userAnswerRepository.findByUserIdAndExamId(user.getId(), examId);

        int correctCount = 0;
        int totalQuestions = answers.size();

        for (UserAnswer answer : answers) {
            Question q = answer.getQuestion();
            if (q.getCorrectAnswer().equals(answer.getSelectedAnswer())) {
                correctCount++;
            }
        }

        int incorrectCount = totalQuestions - correctCount;
        double percentage = totalQuestions == 0 ? 0 : (double) correctCount / totalQuestions * 100;

        ResultResponse result = new ResultResponse();
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctCount);
        result.setIncorrectAnswers(incorrectCount);
        result.setPercentage(percentage);
        result.setPassed(percentage >= 80);

        return ResponseEntity.ok(result);
    }

}
