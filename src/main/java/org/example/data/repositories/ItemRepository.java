package org.example.data.repositories;

import org.example.data.models.Item;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ItemRepository extends MongoRepository<Item, Long> {
}
