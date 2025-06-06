package org.example.exam.dto;

import lombok.Data;

@Data
public class GrantAccessRequest {
    private Long userId;
    private Long examId;
}

