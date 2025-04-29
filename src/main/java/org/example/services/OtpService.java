package org.example.services;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import org.example.data.models.Otp;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.ValidateUserRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class OtpService implements OtpServiceInterface {
    private final MongoOperations otpRepository;
    private final SendEmailService emailService;

    public OtpService(MongoOperations otpRepository, SendEmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }


    @Override
    public String sendOtp(EmailRequest emailRequest) {
        long min = (long) Math.pow(10, 3);
        long otp = ThreadLocalRandom.current().nextLong(min, min * 10);
        Otp otpObj = new Otp();
        otpObj.setOtp(String.valueOf(otp));
        otpObj.setEmail(emailRequest.getEmail());
        otpObj.setExpirationTime(Instant.now().plus(5, ChronoUnit.MINUTES));
        otpRepository.save(otpObj);
        emailService.sendEmail(emailRequest.getEmail(), "OTP Code", otpObj.getOtp());
        return otpObj.getOtp();
    }

    public boolean validateOtp(ValidateUserRequest validateUserRequest) {
        Query query = new Query(
                Criteria.where("otp").is(validateUserRequest.getUserOtp())
                        .and("email").is(validateUserRequest.getEmail())
        );
        Otp otpObj = otpRepository.findOne(query, Otp.class);
        return otpObj != null && Instant.now().isBefore(otpObj.getExpirationTime());
    }
}
