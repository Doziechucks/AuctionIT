package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class CreateAccountRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
