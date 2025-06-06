package org.example.exam.repository;

import org.example.exam.entity.Exam;
import org.example.exam.entity.ExamAccess;
import org.example.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExamAccessRepository extends JpaRepository<ExamAccess, Long> {
    Optional<ExamAccess> findByUserAndExam(User user, Exam exam);
}
