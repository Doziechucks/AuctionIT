package org.example.services;

import org.example.dto.requests.EmailRequest;

public interface OtpServiceInterface {
    public String sendOtp(EmailRequest emailRequest);
}
