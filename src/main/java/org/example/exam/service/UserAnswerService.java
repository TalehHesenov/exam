package org.example.exam.service;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.UserAnswerRequest;
import org.example.exam.entity.Exam;
import org.example.exam.entity.Question;
import org.example.exam.entity.User;
import org.example.exam.entity.UserAnswer;
import org.example.exam.repository.ExamRepository;
import org.example.exam.repository.QuestionRepository;
import org.example.exam.repository.UserAnswerRepository;
import org.example.exam.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAnswerService {

    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final UserAnswerRepository userAnswerRepository;

    public void saveUserAnswer(UserAnswerRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found"));

        boolean isCorrect = question.getCorrectAnswer().equals(request.getSelectedAnswer());

        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUser(user);
        userAnswer.setExam(exam);
        userAnswer.setQuestion(question);
        userAnswer.setSelectedAnswer(request.getSelectedAnswer());
        userAnswer.setCorrect(isCorrect);

        userAnswerRepository.save(userAnswer);
    }
}

