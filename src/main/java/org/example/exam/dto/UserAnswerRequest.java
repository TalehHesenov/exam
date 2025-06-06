package org.example.exam.dto;

import lombok.Data;

@Data
public class UserAnswerRequest {
    private Long examId;
    private Long questionId;
    private String selectedAnswer;
}

