package org.example.exam.service;

import lombok.RequiredArgsConstructor;
import org.example.exam.entity.Exam;
import org.example.exam.entity.ExamAccess;
import org.example.exam.entity.User;
import org.example.exam.enums.ExamLevel;
import org.example.exam.enums.ExamType;
import org.example.exam.repository.ExamAccessRepository;
import org.example.exam.repository.ExamRepository;
import org.example.exam.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.example.exam.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final ExamAccessRepository examAccessRepository;

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public List<Exam> getAllActiveExams() {
        return examRepository.findByIsActiveTrue();
    }

    public Optional<Exam> getExamById(Long id) {
        return examRepository.findById(id);
    }

    public void deleteExam(Long id) {
        examRepository.deleteById(id);
    }

    public Exam updateExam(Long id, Exam updated) {
        Exam exam = examRepository.findById(id).orElseThrow();
        exam.setTitle(updated.getTitle());
        exam.setDescription(updated.getDescription());
        exam.setDurationMinutes(updated.getDurationMinutes());
        exam.setLevel(updated.getLevel());
        exam.setCategory(updated.getCategory());
        exam.setActive(updated.isActive());
        return examRepository.save(exam);
    }
    public ResponseEntity<?> startExam(Long examId, String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        Optional<Exam> examOpt = examRepository.findById(examId);

        if (userOpt.isEmpty() || examOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exam or User not found.");
        }

        User user = userOpt.get();
        Exam exam = examOpt.get();

        Optional<ExamAccess> accessOpt = examAccessRepository.findByUserAndExam(user, exam);
        if (accessOpt.isEmpty() || !accessOpt.get().isAllowed()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not allowed to access this exam.");
        }

        return ResponseEntity.ok(exam);
    }

    public ResponseEntity<String> grantAccessToUser(Long userId, Long examId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Exam> examOpt = examRepository.findById(examId);

        if (userOpt.isEmpty() || examOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Exam not found.");
        }

        ExamAccess access = new ExamAccess();
        access.setUser(userOpt.get());
        access.setExam(examOpt.get());
        access.setAllowed(true);
        examAccessRepository.save(access);

        return ResponseEntity.ok("Access granted to user for this exam.");
    }





}
