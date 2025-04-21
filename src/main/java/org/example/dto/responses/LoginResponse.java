package org.example.dto.responses;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String userId;
    private String email;
}
