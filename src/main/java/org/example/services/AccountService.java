package org.example.services;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.CreateAccountRequest;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.LoginRequest;
import org.example.dto.requests.ValidateUserRequest;
import org.example.dto.responses.CreateAccountResponse;
import org.example.dto.responses.LoginResponse;
import org.example.exceptions.EmailAlreadyExistsException;
import org.example.security.PasswordEncoder;
import org.example.utils.JwtUtils;

import org.springframework.stereotype.Service;


@Service
public class AccountService implements AccountServiceInterface{
    private final UserRepository userRepository;
    private final OtpService otpService;

    String  passwordRegex = "^(?=.*\\d)(?=.*[A-Z])(?=.*[a-z]).{8,}$";
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,15}$";

    public AccountService(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Override
    public CreateAccountResponse createUser(CreateAccountRequest createAccountRequest) {
        if(validateInformationIntegrity(createAccountRequest)) {
            User user = new User(
                    createAccountRequest.getEmail(),
                    createAccountRequest.getFirstName(),
                    createAccountRequest.getLastName(),
                    PasswordEncoder.hashPassword(createAccountRequest.getPassword())
            );
            userRepository.save(user);
            String token = JwtUtils.generateToken(user.getUserId(), user.getFirstName(), user.getLastName());
            return new CreateAccountResponse(token);

        }
        throw new IllegalArgumentException("Invalid Otp try again");
    }

    public boolean validateEmail(EmailRequest emailRequest) {
        if (userRepository.existsByEmail(emailRequest.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + emailRequest.getEmail() + " is already taken");
        }
        if (!emailRequest.getEmail().matches(emailRegex)){
            throw new IllegalArgumentException("Invalid Email try again");
        }
        else return true;
    }

    public boolean ValidateUser(ValidateUserRequest validateUserRequest) {
        boolean result = otpService.validateOtp(validateUserRequest);
        if (result) {
            return true;
        }
        else {
            throw new IllegalArgumentException("Invalid Otp try again");
        }
    }

    @Override
    public String sendOtp(EmailRequest emailRequest) {
        String otp = otpService.sendOtp(emailRequest);
        if (otp != null) {
            return "Otp sent to " + emailRequest.getEmail();
        }
        else {
            return null;
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user != null) {
            if(PasswordEncoder.verifyPassword(loginRequest.getPassword(), user.getPassword()))
            {
                String token = JwtUtils.generateToken(user.getUserId(), user.getFirstName(), user.getEmail());
                return new LoginResponse(token);
            }
            throw new IllegalArgumentException("wrong password");
        }
        throw new IllegalArgumentException("Invalid login detail try again");

    }

    private boolean validateInformationIntegrity(CreateAccountRequest  createAccountRequest){
        if(createAccountRequest.getFirstName() == null || createAccountRequest.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if(createAccountRequest.getLastName() == null || createAccountRequest.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
        if (createAccountRequest.getPassword() == null || createAccountRequest.getPassword().isEmpty() || !createAccountRequest.getPassword().matches(passwordRegex)){
            throw new IllegalArgumentException("Invalid password try again");
        }

        return true;
    }

}
