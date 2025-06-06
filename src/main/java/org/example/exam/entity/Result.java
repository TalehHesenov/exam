package org.example.exam.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "results")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    private int score;

    private LocalDateTime submittedAt;

    @ElementCollection
    @CollectionTable(name = "result_correct_questions", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "question_id")
    private List<Long> correctQuestionIds;

    @ElementCollection
    @CollectionTable(name = "result_wrong_questions", joinColumns = @JoinColumn(name = "result_id"))
    @Column(name = "question_id")
    private List<Long> wrongQuestionIds;
}
