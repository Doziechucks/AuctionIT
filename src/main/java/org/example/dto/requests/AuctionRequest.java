package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.data.models.ItemCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data @AllArgsConstructor
public class AuctionRequest {
    private String itemName;
    private String description;
    private ItemCategory category;
    private String sellerId;
    private String currentBidderId;
    private LocalDate postTime;
    private LocalDate startTime;
    private LocalDate endTime;
    private BigDecimal startingPrice;
    private BigDecimal currentPrice;
    private List<String> itemUrls;
}
