package org.example.services;

import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.exceptions.EmailAlreadyExistsException;
import org.example.security.PasswordEncoder;

public class AccountService implements AccountServiceInterface{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException("Email " + user.getEmail() + " is already taken");
        }
        user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
        return userRepository.save(user);
    }
}
