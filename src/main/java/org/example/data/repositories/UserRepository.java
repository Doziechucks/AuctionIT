package org.example.data.repositories;

import org.example.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
