package org.example.services;

import org.example.data.models.User;
import org.example.dto.requests.LoginRequest;

public interface AccountServiceInterface {
    User createUser(User user, String otp);
    public String sendOtp(String email);
    public Lo login(LoginRequest loginRequest);
}
