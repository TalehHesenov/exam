package org.example.exam.service;


import lombok.RequiredArgsConstructor;
import org.example.exam.entity.Exam;
import org.example.exam.repository.ExamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;

    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }
}

