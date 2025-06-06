package org.example.exam.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultDTO {
    private Long id;
    private Long userId;
    private Long examId;
    private int score;
    private LocalDateTime submittedAt;
    private List<Long> correctQuestionIds;
    private List<Long> wrongQuestionIds;
}

