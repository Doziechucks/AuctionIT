package org.example.services;

import org.example.data.models.User;
import org.example.dto.requests.CreateAccountRequest;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.LoginRequest;
import org.example.dto.requests.ValidateUserRequest;
import org.example.dto.responses.CreateAccountResponse;
import org.example.dto.responses.LoginResponse;

public interface AccountServiceInterface {
    public CreateAccountResponse createUser(CreateAccountRequest createAccountRequest);
    public String sendOtp(EmailRequest emailRequest);
    public boolean ValidateUser(ValidateUserRequest validateUserRequest);
    public LoginResponse login(LoginRequest loginRequest);
}
