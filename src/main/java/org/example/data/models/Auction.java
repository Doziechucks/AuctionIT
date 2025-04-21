package org.example.data.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "auctions")
@Getter @AllArgsConstructor @NoArgsConstructor
public class Auction {
    @Id
    private String auctionId;
    @Setter
    private LocalDate postTime;
    @Setter
    private LocalDate startTime;
    @Setter
    private LocalDate endTime;
    @Setter
    private String itemId;
    @Setter
    private String posterId;
    @Setter
    private BigDecimal price;
    @Setter
    private String sellerId;
    @Setter
    private String bidderId;

}
