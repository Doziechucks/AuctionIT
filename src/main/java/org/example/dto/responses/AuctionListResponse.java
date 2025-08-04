package org.example.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuctionListResponse {
    List<AuctionResponse> auctions;

}
