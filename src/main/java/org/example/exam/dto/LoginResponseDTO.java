package org.example.exam.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserDTO user;
}

