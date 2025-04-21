package org.example.data.repositories;

import org.example.data.models.Otp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends MongoRepository<Otp, String>{
}
