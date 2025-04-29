package org.example.controllers;


import org.example.dto.requests.*;
import org.example.dto.responses.AuctionListResponse;
import org.example.dto.responses.AuctionResponse;
import org.example.services.AuctionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @PostMapping("/create-auction")
    public ResponseEntity<AuctionResponse> createAuction(
            @RequestBody AuctionRequest auctionRequest) {
        AuctionResponse auctionResponse = auctionService.createAuction(auctionRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(auctionResponse);
    }

    @GetMapping("/{auctionId}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable String auctionId) {
        AuctionResponse response = auctionService.getAuctionById(auctionId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AuctionListResponse>> getAllItems() {
        List<AuctionListResponse> responses = auctionService.getAllItems();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<AuctionListResponse>> getAuctionByName(@RequestParam AuctionNameRequest request) {
        List<AuctionListResponse> responses = auctionService.getAuctionByName(request);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-category")
    public ResponseEntity<List<AuctionListResponse>> getAuctionByCategory(@RequestParam AuctionCategoryRequest categoryRequest) {
        List<AuctionListResponse> responses = auctionService.getItemByCategory(categoryRequest);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-start-date")
    public ResponseEntity<List<AuctionListResponse>> getAuctionsByStartDate(@RequestParam AuctionTimeRequests time) {
        List<AuctionListResponse> responses = auctionService.getAuctionByStartDate(time);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-end-ddate")
    public ResponseEntity<List<AuctionListResponse>> getAuctionsByEndDate(@RequestParam AuctionTimeRequests time) {
        List<AuctionListResponse> responses = auctionService.getAuctionByEndDate(time);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/by-post-date")
    public ResponseEntity<List<AuctionListResponse>> getAuctionsByPostDate(@RequestParam AuctionTimeRequests time) {
        List<AuctionListResponse> responses = auctionService.getAuctionByPostDate(time);
        return ResponseEntity.ok(responses);
    }


}
