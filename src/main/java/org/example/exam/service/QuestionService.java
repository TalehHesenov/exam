package org.example.exam.service;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.QuestionDto;
import org.example.exam.entity.Exam;
import org.example.exam.entity.Question;
import org.example.exam.repository.ExamRepository;
import org.example.exam.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;

    public List<QuestionDto> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public QuestionDto createQuestion(QuestionDto questionDto) {
        Question question = mapToEntity(questionDto);
        Question saved = questionRepository.save(question);
        return mapToDto(saved);
    }

    public QuestionDto updateQuestion(Long id, QuestionDto questionDto) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id " + id));

        question.setQuestionText(questionDto.getQuestionText());
        question.setOptionA(questionDto.getOptionA());
        question.setOptionB(questionDto.getOptionB());
        question.setOptionC(questionDto.getOptionC());
        question.setOptionD(questionDto.getOptionD());
        question.setCorrectOption(questionDto.getCorrectOption());

        // Update exam association if examId provided
        if (questionDto.getExamId() != null) {
            Exam exam = examRepository.findById(questionDto.getExamId())
                    .orElseThrow(() -> new RuntimeException("Exam not found with id " + questionDto.getExamId()));
            question.setExam(exam);
        } else {
            question.setExam(null);
        }

        Question updated = questionRepository.save(question);
        return mapToDto(updated);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    private QuestionDto mapToDto(Question question) {
        QuestionDto dto = new QuestionDto();
        dto.setId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setOptionA(question.getOptionA());
        dto.setOptionB(question.getOptionB());
        dto.setOptionC(question.getOptionC());
        dto.setOptionD(question.getOptionD());
        dto.setCorrectOption(question.getCorrectOption());

        if (question.getExam() != null) {
            dto.setExamId(question.getExam().getId());
        }

        return dto;
    }

    private Question mapToEntity(QuestionDto dto) {
        Question question = new Question();
        question.setId(dto.getId());
        question.setQuestionText(dto.getQuestionText());
        question.setOptionA(dto.getOptionA());
        question.setOptionB(dto.getOptionB());
        question.setOptionC(dto.getOptionC());
        question.setOptionD(dto.getOptionD());
        question.setCorrectOption(dto.getCorrectOption());

        if (dto.getExamId() != null) {
            Exam exam = examRepository.findById(dto.getExamId())
                    .orElseThrow(() -> new RuntimeException("Exam not found with id " + dto.getExamId()));
            question.setExam(exam);
        }

        return question;
    }
}
