package org.example.exam.controller;

import lombok.RequiredArgsConstructor;
import org.example.exam.dto.GrantAccessRequest;
import org.example.exam.entity.Question;
import org.example.exam.payload.QuestionRequest;
import org.example.exam.service.ExamService;
import org.example.exam.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final ExamService examService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Question> addQuestion(@RequestBody QuestionRequest request) {
        return ResponseEntity.ok(
                questionService.addQuestionToExam(request.getExamId(), request.toQuestion())
        );
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<List<Question>> getQuestionsByExam(@PathVariable Long examId) {
        return ResponseEntity.ok(questionService.getQuestionsByExamId(examId));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody QuestionRequest request) {
        Question updated = request.toQuestion();
        updated.setId(id);
        return ResponseEntity.ok(questionService.updateQuestion(id, updated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/grant-access")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> grantAccess(@RequestBody GrantAccessRequest request) {
        return examService.grantAccessToUser(request.getUserId(), request.getExamId());
    }

}
