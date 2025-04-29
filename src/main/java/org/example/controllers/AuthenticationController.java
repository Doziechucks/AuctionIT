package org.example.controllers;

import org.example.dto.requests.CreateAccountRequest;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.LoginRequest;
import org.example.dto.requests.ValidateUserRequest;
import org.example.dto.responses.CreateAccountResponse;
import org.example.dto.responses.LoginResponse;
import org.example.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AccountService accountService;

    public AuthenticationController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateAccountResponse> register(
            @RequestBody CreateAccountRequest request) {
        CreateAccountResponse response = accountService.createUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate-email")
    public ResponseEntity<Boolean> validateEmail(@RequestBody EmailRequest emailRequest) {
        boolean result = accountService.validateEmail(emailRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<String> sendOtp(@RequestBody EmailRequest emailRequest) {
        String result = accountService.sendOtp(emailRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/validate-user")
    public ResponseEntity<Boolean> validateUser(@RequestBody ValidateUserRequest emailRequest) {
        boolean result = accountService.ValidateUser(emailRequest);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = accountService.login(request);
        return ResponseEntity.ok(response);
    }

}
