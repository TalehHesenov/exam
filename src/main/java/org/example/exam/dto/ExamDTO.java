package org.example.exam.dto;

import lombok.*;
import org.example.exam.enums.ExamLevel;
import org.example.exam.enums.ExamType;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDTO {
    private Long id;
    private ExamType type;
    private ExamLevel level;
    private LocalDateTime startTime;
    private int durationMinutes;
    private boolean active;
}

