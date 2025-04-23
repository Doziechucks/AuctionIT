package org.example.services;

import org.example.dto.requests.*;
import org.example.dto.responses.AuctionListResponse;
import org.example.dto.responses.AuctionResponse;

import java.util.List;

public interface AuctionServiceInterface {
    AuctionResponse createAuction(AuctionRequest auctionRequestRequest);
    AuctionResponse getAuctionById(String auctionId);
    AuctionResponse updateItem(UpdateRequest updateRequest);
    List<AuctionListResponse>  getAllItems();
    List<AuctionListResponse> getAuctionByName(AuctionNameRequest name);
    List<AuctionListResponse> getItemByCategory(AuctionCategoryRequest category);
    List<AuctionListResponse> getAuctionByStartDate(AuctionTimeRequests startDate);
    List<AuctionListResponse> getAuctionByEndDate(AuctionTimeRequests endDate);
    List<AuctionListResponse> getAuctionByPostDate(AuctionTimeRequests postDate);

}
