package org.example.data.repositories;

import org.example.data.models.Auction;
import org.example.data.models.ItemCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuctionRepository extends MongoRepository<Auction, String> {
    List<Auction> findByStartTime(LocalDate startTime);
    List<Auction> findByEndTime(LocalDate endTime);
    List<Auction> findByPostTime(LocalDate postTime);
    List<Auction> findByItemName(String name);
    List<Auction> findByItemCategory(ItemCategory itemCategory);
}
