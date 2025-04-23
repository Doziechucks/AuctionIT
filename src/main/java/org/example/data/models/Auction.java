package org.example.data.models;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "auctions")
@Getter
public class Auction {
    @Id
    private String auctionId;
    @Setter
    private String itemName;
    @Setter
    private String itemDescription;
    @Setter
    private ItemCategory itemCategory;
    @Setter
    private String sellerId;
    @Setter
    private String currentBidderId;
    @Setter
    private LocalDate postTime;
    @Setter
    private LocalDate startTime;
    @Setter
    private LocalDate endTime;
    @Setter
    private BigDecimal startingPrice;
    @Setter
    private BigDecimal currentPrice;



    public Auction(String itemName, String itemDescription, ItemCategory itemCategory, String sellerId, String currentBidderId, LocalDate postTime, LocalDate startTime, LocalDate endTime, BigDecimal startingPrice, BigDecimal currentPrice) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemCategory = itemCategory;
        this.sellerId = sellerId;
        this.currentBidderId = currentBidderId;
        this.postTime = postTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startingPrice = startingPrice;
        this.currentPrice = currentPrice;

    }


}

