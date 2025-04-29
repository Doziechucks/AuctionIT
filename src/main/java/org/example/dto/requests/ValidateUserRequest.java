package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ValidateUserRequest {
    private String email;
    private String userOtp;
}
