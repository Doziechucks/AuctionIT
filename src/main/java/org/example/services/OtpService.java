package org.example.services;


import java.util.concurrent.ThreadLocalRandom;
import org.example.data.models.Otp
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class OtpService implements OtpServiceInterface {
    private final MongoOperations otpRepository;


    public OtpService(MongoOperations otpRepository) {
        this.otpRepository = otpRepository;
    }


    @Override
    public String sendOtp(String email) {
        long min = (long) Math.pow(10, 3);
        long otp = ThreadLocalRandom.current().nextLong(min, min * 10);
        Otp otpObj = new Otp();
        otpObj.setOtp(String.valueOf(otp));
        otpRepository.save(otpObj);
        emailServiceImpl.sendSimpleMail(email, "OTP Code", otpObj.getCode());
        return otpObj.getCode();
    }

    }
}
