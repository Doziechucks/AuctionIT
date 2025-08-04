package org.example.services;

import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.ValidateUserRequest;

public interface OtpServiceInterface {
    public String sendOtp(EmailRequest emailRequest);
    public boolean validateOtp(ValidateUserRequest validateUserRequest);
}
