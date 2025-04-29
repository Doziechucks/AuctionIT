package org.example.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "otp")
@Data
@NoArgsConstructor
public class Otp {
    @Id
    private  String OtpId;
    private String otp;
    private String email;
    private Instant expirationTime;
}
