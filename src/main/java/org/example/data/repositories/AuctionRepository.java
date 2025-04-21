package org.example.data.repositories;

import org.example.data.models.Auction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface AuctionRepository extends MongoRepository<Auction, String> {
    List<Auction> findByStartTime(LocalDate startTime);
    List<Auction> findByEndTime(LocalDate endTime);
}
