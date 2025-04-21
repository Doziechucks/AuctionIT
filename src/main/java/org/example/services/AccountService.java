package org.example.services;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.LoginRequest;
import org.example.exceptions.EmailAlreadyExistsException;
import org.example.security.PasswordEncoder;

import java.util.Objects;

public class AccountService implements AccountServiceInterface{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpService otpService;
    private final LoginRequest loginRequest;

    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService, LoginRequest loginRequest) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
        this.loginRequest = loginRequest;
    }

    public User createUser(User user, String otp) {
        if (validateUser(user) && Objects.equals(sendOtp(user.getEmail()), otp)) {
            user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
            return userRepository.save(user);
        }
        throw new IllegalArgumentException("Invalid Otp try again");
    }
    private boolean validateUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + user.getEmail() + " is already taken");
        }
        else return true;
    }

    @Override
    public String sendOtp(String email) {
        return otpService.sendOtp(email);
    }

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        return "";
    }


}
