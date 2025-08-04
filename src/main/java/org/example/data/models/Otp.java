package org.example.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "OTPs")
@Data

public class Otp {
    @Id
    private  String otpId;
    private String otp;
    private String email;
    private Instant expirationTime;

    public Otp(String otp, String email, Instant expirationTime) {
        this.otp = otp;
        this.email = email;
        this.expirationTime = expirationTime;

    }


}
