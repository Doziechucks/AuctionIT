package org.example.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class UpdateRequest {
    private String auctionId;
    private String currentBidderId;
    public BigDecimal currentPrice;
}
