package org.example.services;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import org.example.data.models.Otp;
import org.example.data.repositories.OtpRepository;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.ValidateUserRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class OtpService implements OtpServiceInterface {
    private static final int OTP_LENGTH = 4;  // 4-digit OTP
    private static final int EXPIRY_MINUTES = 5;

    private final MongoTemplate mongoTemplate;
    private final SendEmailService emailService;

    public OtpService(MongoTemplate mongoTemplate, SendEmailService emailService) {
        this.mongoTemplate = mongoTemplate;
        this.emailService = emailService;
    }



    @Override
    public String sendOtp(EmailRequest emailRequest) {
        long min = (long) Math.pow(10, OTP_LENGTH-1);
        String otp = String.valueOf(ThreadLocalRandom.current().nextLong(min, min * 10));
        Instant expirationTime = Instant.now().plus(EXPIRY_MINUTES, ChronoUnit.MINUTES);
        Otp otpObj = new Otp(otp, emailRequest.getEmail(), expirationTime);
        mongoTemplate.save(otpObj);
        emailService.sendEmail(emailRequest.getEmail(), "OTP Code", otp);
        return otp;
    }

    @Override
    public boolean validateOtp(ValidateUserRequest validateUserRequest) {
        Query query = new Query(
                Criteria.where("otp").is(validateUserRequest.getUserOtp())
                        .and("email").is(validateUserRequest.getEmail())
        );
        Otp otpObj = mongoTemplate.findOne(query, Otp.class);
        return otpObj != null && Instant.now().isBefore(otpObj.getExpirationTime());
    }
}
