package org.example.services;

import com.mongodb.client.MongoClients;
import org.example.Main;
import org.example.data.models.Otp;
import org.example.data.models.User;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.CreateAccountRequest;
import org.example.dto.requests.EmailRequest;
import org.example.dto.requests.LoginRequest;
import org.example.dto.responses.CreateAccountResponse;
import org.example.dto.responses.LoginResponse;
import org.example.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.mockito.Mockito;

import java.time.Instant;



@SpringBootTest(classes = Main.class)
@Testcontainers
public class AccountServiceTest {

    @Container
    private static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                    .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
        registry.add("spring.mail.enabled", () -> "false"); // Disable real email sending
    }


    private static MongoTemplate mongoTemplate;
    private static AccountService accountService;
    private static OtpService otpService;
    private static SendEmailService emailServiceMock;

    @BeforeAll
    static void setup() {
        String connectionString = mongoDBContainer.getConnectionString();
        MongoClient mongoClient = MongoClients.create(connectionString);
        mongoTemplate = new MongoTemplate(mongoClient, "testDb");

        emailServiceMock = Mockito.mock(SendEmailService.class);
        otpService = new OtpService(mongoTemplate, emailServiceMock);
        UserRepository userRepository = new MongoRepositoryFactory(mongoTemplate)
                .getRepository(UserRepository.class);

        accountService = new AccountService(userRepository, otpService);
    }

    @BeforeEach
    public void setUp() {
        mongoTemplate.dropCollection(User.class);
        mongoTemplate.dropCollection(Otp.class);
    }

    @Test
    void createAccountIsSentToDatabase() {
        String testEmail = "test@example.com";
        CreateAccountRequest request = new CreateAccountRequest(
                testEmail,
                "John",
                "Doe",
                "ValidPass123!"
        );
        CreateAccountResponse response = accountService.createUser(request);
        Assertions.assertNotNull(response);
        User savedUser = mongoTemplate.findOne(new Query(), User.class);
        Assertions.assertNotNull(savedUser);
        Assertions.assertEquals("John", savedUser.getFirstName());
        Assertions.assertEquals("Doe", savedUser.getLastName());
        Assertions.assertEquals(testEmail, savedUser.getEmail());
    }

    @Test
    void createUser_shouldThrowWhenEmailAlreadyRegistered(){
            String testEmail = "test@example.com";
            mongoTemplate.save(new Otp("000000", testEmail, Instant.now().plusSeconds(180)));
            CreateAccountRequest request = new CreateAccountRequest(
                    testEmail,
                    "John",
                    "Doe",
                    "ValidPass123!"
            );
            CreateAccountResponse response = accountService.createUser(request);
            Assertions.assertNotNull(response);

            EmailRequest emailRequest = new EmailRequest(testEmail);
            Assertions.assertThrows(EmailAlreadyExistsException.class, () -> {
                accountService.validateEmail(emailRequest);
            });

    }

    @Test
    void testThatRegisteredUserCanLogin() {
        String testEmail = "test@example.com";
        mongoTemplate.save(new Otp("000000", testEmail, Instant.now().plusSeconds(180)));
        CreateAccountRequest request = new CreateAccountRequest(
                testEmail,
                "john",
                "cock",
                "ValidPass123!"
        );
        CreateAccountResponse response = accountService.createUser(request);
        Assertions.assertNotNull(response);
        LoginRequest loginRequest = new LoginRequest(testEmail, "ValidPass123!");
        LoginResponse loginResponse = accountService.login(loginRequest);
        Assertions.assertNotNull(loginResponse);
    }



}
