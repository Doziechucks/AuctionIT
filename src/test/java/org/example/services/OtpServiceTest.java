package org.example.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.example.Main;
import org.example.data.models.Otp;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.ValidateUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

@SpringBootTest(classes = Main.class)
@Testcontainers
class OtpServiceTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.mail.enabled", () -> "false"); // Disable real email sending
    }

    private static MongoTemplate mongoTemplate;
    private SendEmailService emailService;
    private OtpService otpService;


    @BeforeEach
    void setUp() {
        MongoClient mongoClient = MongoClients.create(mongoDBContainer.getReplicaSetUrl());
        mongoTemplate = new MongoTemplate(mongoClient, "testdb");
        mongoTemplate.dropCollection(Otp.class);
        emailService = mock(SendEmailService.class);
        otpService = new OtpService(mongoTemplate, emailService);
    }


    @AfterEach
    void verifyNoUnexpectedEmailInteractions() {
        verifyNoMoreInteractions(emailService);
    }

    @Test
    void sendOtpPersistCorrectly() {
        EmailRequest request = new EmailRequest("test@example.com");
        String otp = otpService.sendOtp(request);
        Otp savedOtp = mongoTemplate.findAll(Otp.class).get(0);
        assertEquals(otp, savedOtp.getOtp());
        assertEquals("test@example.com", savedOtp.getEmail());
        assertTrue(savedOtp.getExpirationTime().isAfter(Instant.now()));
        verify(emailService).sendEmail("test@example.com", "OTP Code", otp);

    }
    @Test
    void TestThatValidateOtpReturnTrueForValidOtp(){
        ValidateUserRequest userRequest = new ValidateUserRequest("test@gmail.com", "1234");
        Otp validOtp = new Otp("1234", "test@gmail.com", Instant.now().plusSeconds(240));
        mongoTemplate.insert(validOtp);

        boolean isValidOtp = otpService.validateOtp(userRequest);
        assertTrue(isValidOtp);

    }

    @Test
    void TestThatValidateOtpReturnFalseForValidOtp(){
        ValidateUserRequest userRequest = new ValidateUserRequest("test@gmail.com", "1334");
        Otp validOtp = new Otp("1234", "test@gmail.com", Instant.now().plusSeconds(240));
        mongoTemplate.insert(validOtp);

        boolean isValidOtp = otpService.validateOtp(userRequest);
        assertFalse(isValidOtp);

    }

}