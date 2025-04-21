package org.example.data.models;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
public class Otp {
    @Id
    private  String OtpId;
    private String otp;
}
