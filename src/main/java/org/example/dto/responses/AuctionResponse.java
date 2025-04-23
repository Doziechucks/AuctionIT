package org.example.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.data.models.ItemCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AuctionResponse {
    private String auctionId;
    private String itemName;
    private String itemDescription;
    private ItemCategory category;
    private String sellerId;
    private String currentBidderId;
    private LocalDate postTime;
    private LocalDate startTime;
    private LocalDate endTime;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;

}
