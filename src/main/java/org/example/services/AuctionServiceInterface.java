package org.example.services;

import org.example.dto.requests.*;
import org.example.dto.responses.AuctionListResponse;
import org.example.dto.responses.AuctionResponse;

import java.util.List;

public interface AuctionServiceInterface {
    AuctionResponse createAuction(AuctionRequest auctionRequestRequest);
    AuctionResponse getAuctionById(String auctionId);
    AuctionResponse updateItem(UpdateRequest updateRequest);
    List<AuctionResponse>  getAllItems();
    List<AuctionResponse> getAuctionByName(AuctionNameRequest name);
    List<AuctionResponse> getItemByCategory(AuctionCategoryRequest category);
    List<AuctionResponse> getAuctionByStartDate(AuctionTimeRequests startDate);
    List<AuctionResponse> getAuctionByEndDate(AuctionTimeRequests endDate);
    List<AuctionResponse> getAuctionByPostDate(AuctionTimeRequests postDate);

}
