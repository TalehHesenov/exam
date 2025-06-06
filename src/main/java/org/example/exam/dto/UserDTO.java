package org.example.exam.dto;

import lombok.*;
import org.example.exam.enums.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String fullName;
    private String email;
    private Role role;
    private boolean isEnabled;
}

