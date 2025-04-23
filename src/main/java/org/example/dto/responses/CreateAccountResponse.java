package org.example.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class CreateAccountResponse {
    private String token;
}
