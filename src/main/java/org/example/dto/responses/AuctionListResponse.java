package org.example.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuctionListResponse {
    private String auctionId;
    private String itemName;

}
