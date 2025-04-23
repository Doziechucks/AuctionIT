package org.example.services;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.CreateAccountRequest;
import org.example.dto.requests.LoginRequest;
import org.example.dto.responses.CreateAccountResponse;
import org.example.dto.responses.LoginResponse;
import org.example.exceptions.EmailAlreadyExistsException;
import org.example.security.PasswordEncoder;
import org.example.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class AccountService implements AccountServiceInterface{
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final LoginRequest loginRequest;
    private final JwtUtils jwtUtils;
    private final LoginResponse loginResponse;

    public AccountService(UserRepository userRepository, OtpService otpService, LoginRequest loginRequest, JwtUtils jwtUtils, LoginResponse loginResponse) {
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.loginRequest = loginRequest;
        this.jwtUtils = jwtUtils;
        this.loginResponse = loginResponse;
    }

    @Override
    public CreateAccountResponse createUser(CreateAccountRequest createAccountRequest, String otp) {
        if (validateUser(createAccountRequest) && Objects.equals(sendOtp(createAccountRequest.getEmail()), otp)) {
            createAccountRequest.setPassword(PasswordEncoder.hashPassword(createAccountRequest.getPassword()));
            User user = new User(createAccountRequest.getEmail(), createAccountRequest.getFirstName(), createAccountRequest.getLastName(), createAccountRequest.getPassword());
            userRepository.save(user);
            String token = JwtUtils.generateToken(user.getUserId(), user.getFirstName(), user.getLastName());
            return new CreateAccountResponse(token);
        }
        throw new IllegalArgumentException("Invalid Otp try again");
    }

    private boolean validateUser(CreateAccountRequest createAccountRequest) {
        if (userRepository.existsByEmail(createAccountRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + createAccountRequest.getEmail() + " is already taken");
        }
        else return true;
    }

    @Override
    public String sendOtp(String email) {
        return otpService.sendOtp(email);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user != null) {
            if(user.getPassword().equals(PasswordEncoder.hashPassword(loginRequest.getPassword()))) {
                String token = JwtUtils.generateToken(user.getUserId(), user.getFirstName(), user.getEmail());
                return new LoginResponse(token);
            }
        }
        throw new IllegalArgumentException("Invalid login detail try again");

    }


}
