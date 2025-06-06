package org.example.exam.service;

import lombok.RequiredArgsConstructor;
import org.example.exam.entity.Exam;
import org.example.exam.entity.Question;
import org.example.exam.repository.ExamRepository;
import org.example.exam.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public Question addQuestionToExam(Long examId, Question question) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("Exam tapılmadı: " + examId));
        question.setExam(exam);
        return questionRepository.save(question);
    }

    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    public Optional<Question> getById(Long id) {
        return questionRepository.findById(id);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Long id, Question updated) {
        Question question = questionRepository.findById(id).orElseThrow();
        question.setQuestionText(updated.getQuestionText());
        question.setOptions(updated.getOptions());
        question.setCorrectAnswer(updated.getCorrectAnswer());
        return questionRepository.save(question);
    }

}
