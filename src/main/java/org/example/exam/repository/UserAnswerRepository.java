package org.example.exam.repository;


import org.example.exam.entity.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("SELECT ua FROM UserAnswer ua WHERE ua.user.id = :userId AND ua.question.exam.id = :examId")
    List<UserAnswer> findByUserIdAndExamId(@Param("userId") Long userId, @Param("examId") Long examId);
}



