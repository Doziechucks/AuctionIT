package org.example.services;

import org.example.dto.requests.*;
import org.example.data.models.Auction;
import org.example.data.repositories.AuctionRepository;
import org.example.dto.responses.AuctionListResponse;
import org.example.dto.responses.AuctionResponse;

import java.util.List;
import java.util.stream.Collectors;


public class AuctionService implements AuctionServiceInterface{
    private final AuctionRepository auctionRepository;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    @Override
    public AuctionResponse createAuction(AuctionRequest auctionRequest) {
        Auction auction = new Auction(
                auctionRequest.getItemName(),
                auctionRequest.getDescription(),
                auctionRequest.getCategory(),
                auctionRequest.getSellerId(),
                auctionRequest.getCurrentBidderId(),
                auctionRequest.getPostTime(),
                auctionRequest.getStartTime(),
                auctionRequest.getEndTime(),
                auctionRequest.getStartingPrice(),
                auctionRequest.getCurrentPrice()
        );
        auctionRepository.save(auction);
        return new AuctionResponse(
                auction.getAuctionId(),
                auction.getItemName(),
                auction.getItemDescription(),
                auction.getItemCategory(),
                auction.getSellerId(),
                auction.getCurrentBidderId(),
                auction.getPostTime(),
                auction.getStartTime(),
                auction.getEndTime(),
                auction.getStartingPrice(),
                auction.getCurrentPrice()
        );
    }

    @Override
    public AuctionResponse getAuctionById(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElse(null);
        if (auction == null){
            throw new IllegalArgumentException("Auction ID not found");
        }
        return new AuctionResponse(
                auction.getAuctionId(),
                auction.getItemName(),
                auction.getItemDescription(),
                auction.getItemCategory(),
                auction.getSellerId(),
                auction.getCurrentBidderId(),
                auction.getPostTime(),
                auction.getStartTime(),
                auction.getEndTime(),
                auction.getStartingPrice(),
                auction.getCurrentPrice()
        );
    }

    @Override
    public AuctionResponse updateItem(UpdateRequest updateRequest) {
        Auction auction = auctionRepository.findById(updateRequest.getAuctionId()).orElse(null);
        if(auction == null){
            throw new IllegalArgumentException("Auction ID not found");
        }
        auction.setCurrentBidderId(updateRequest.getCurrentBidderId());
        auction.setCurrentPrice(updateRequest.getCurrentPrice());
        auctionRepository.save(auction);
        return new AuctionResponse(
                auction.getAuctionId(),
                auction.getItemName(),
                auction.getItemDescription(),
                auction.getItemCategory(),
                auction.getSellerId(),
                auction.getCurrentBidderId(),
                auction.getPostTime(),
                auction.getStartTime(),
                auction.getEndTime(),
                auction.getStartingPrice(),
                auction.getCurrentPrice()
        );

    }


    @Override
    public List<AuctionListResponse>  getAllItems() {
        List<Auction> auctions = auctionRepository.findAll();
        return  auctions.stream()
                .map(auction -> new AuctionListResponse(
                        auction.getAuctionId(),
                        auction.getItemName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionListResponse> getAuctionByName(AuctionNameRequest name) {
        List<Auction> auctions = auctionRepository.findByItemName(name.getName());
        return auctions.stream()
                .map(auction -> new AuctionListResponse(
                        auction.getAuctionId(),
                        auction.getItemName()
                ))
                .collect(Collectors.toList());
    }


    @Override
    public List<AuctionListResponse> getItemByCategory(AuctionCategoryRequest category) {
        List<Auction> auctions = auctionRepository.findByItemCategory(category.getCategory());
        return auctions.stream()
                .map(auction -> new AuctionListResponse(
                        auction.getAuctionId(),
                        auction.getItemName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionListResponse> getAuctionByStartDate(AuctionTimeRequests startDate) {
        List<Auction> auctions = auctionRepository.findByStartTime(startDate.getDate());
        return auctions.stream()
                .map(auction -> new AuctionListResponse(
                        auction.getAuctionId(),
                        auction.getItemName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionListResponse> getAuctionByEndDate(AuctionTimeRequests endDate) {
        List<Auction> auctions = auctionRepository.findByEndTime(endDate.getDate());
        return auctions.stream()
                .map(auction -> new AuctionListResponse(
                        auction.getAuctionId(),
                        auction.getItemName()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionListResponse> getAuctionByPostDate(AuctionTimeRequests postDate) {
        List<Auction> auctions = auctionRepository.findByPostTime(postDate.getDate());
        return auctions.stream()
                .map(auction -> new AuctionListResponse(
                        auction.getAuctionId(),
                        auction.getItemName()
                ))
                .collect(Collectors.toList());
    }


}
