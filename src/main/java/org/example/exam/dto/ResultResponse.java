package org.example.exam.dto;

import lombok.Data;

@Data
public class ResultResponse {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
    private double percentage;
    private boolean passed;
}
