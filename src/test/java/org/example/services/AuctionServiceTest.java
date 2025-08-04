package org.example.services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.example.Main;
import org.example.data.models.Auction;
import org.example.data.models.ItemCategory;
import org.example.data.models.User;
import org.example.data.repositories.AuctionRepository;
import org.example.data.repositories.UserRepository;
import org.example.dto.requests.AuctionCategoryRequest;
import org.example.dto.requests.AuctionRequest;
import org.example.dto.responses.AuctionListResponse;
import org.example.dto.responses.AuctionResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Main.class)
class AuctionServiceTest {
    @Container
    private static final MongoDBContainer mongoDBContainer =
            new MongoDBContainer(DockerImageName.parse("mongo:latest"))
                    .withExposedPorts(27017);

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        // ✅ Correct: Use method reference (executed LATER when container is ready)
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    private static AuctionService auctionService;
    private static MongoTemplate mongoTemplate;

    @BeforeAll
    static void setUp() {
        mongoDBContainer.start();
        String connectionString = mongoDBContainer.getConnectionString();
        MongoClient mongoClient = MongoClients.create(connectionString);
        mongoTemplate = new MongoTemplate(mongoClient, "testDb");
        AuctionRepository auctionRepository = new MongoRepositoryFactory(mongoTemplate)
                .getRepository(AuctionRepository.class);
        auctionService = new AuctionService(auctionRepository);
    }

    @BeforeEach
    public void setup() {
        mongoTemplate.dropCollection(Auction.class);
    }

    @Test
    void createAuctionTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
        LocalDate date = LocalDate.parse("5/4/2025 5:30 PM", formatter);
        LocalDate endDate = LocalDate.parse("5/4/2025 7:30 PM", formatter);
        List<String> urls = Arrays.asList("man", "woman");
        BigDecimal price = BigDecimal.valueOf(100.305);
        AuctionRequest auctionRequest = new AuctionRequest("dildo", "for servicing engines", ItemCategory.FURNITURE, "namebethat", "badbitch", date, date, endDate, price, price, urls);
        AuctionResponse auctionResponse = auctionService.createAuction(auctionRequest);
        assertNotNull(auctionResponse);
    }

    @Test
    void getAuctionTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
        LocalDate date = LocalDate.parse("5/4/2025 5:30 PM", formatter);
        LocalDate endDate = LocalDate.parse("5/4/2025 7:30 PM", formatter);
        List<String> urls = Arrays.asList("man", "woman");
        BigDecimal price = BigDecimal.valueOf(100.305);
        AuctionRequest auctionRequest = new AuctionRequest("dildo", "for servicing engines", ItemCategory.FURNITURE, "namebethat", "badbitch", date, date, endDate, price, price, urls);
        AuctionResponse auctionResponse = auctionService.createAuction(auctionRequest);
        assertNotNull(auctionResponse);
        List<AuctionResponse> furnitureAuctionsActual = new ArrayList<>();
        furnitureAuctionsActual.add(auctionResponse);

        LocalDate dateTwo = LocalDate.parse("5/4/2025 5:30 PM", formatter);
        LocalDate endDateTwo = LocalDate.parse("5/4/2025 7:30 PM", formatter);
        AuctionRequest auctionRequestTwo = new AuctionRequest("dildo", "for servicing engines", ItemCategory.FURNITURE, "namebethat", "badbitch", dateTwo, dateTwo, endDateTwo, price, price, urls);
        AuctionResponse auctionResponseTwo = auctionService.createAuction(auctionRequestTwo);
        assertNotNull(auctionResponseTwo);
        furnitureAuctionsActual.add(auctionResponseTwo);

        AuctionRequest auctionRequestOne = new AuctionRequest("babyoil", "oiling enginne", ItemCategory.FOOD, "stillme", "ellamia", date, dateTwo, endDate, price, price, urls);
        AuctionResponse auctionResponseOne = auctionService.createAuction(auctionRequestOne);
        assertNotNull(auctionResponseOne);
        AuctionCategoryRequest itemCategory = new AuctionCategoryRequest(ItemCategory.FURNITURE);
        List<AuctionResponse> furnitureAuctions = auctionService.getItemByCategory(itemCategory);
        assertEquals(furnitureAuctionsActual.size(), furnitureAuctions.size());




    }

}