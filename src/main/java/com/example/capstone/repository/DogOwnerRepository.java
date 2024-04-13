package com.example.capstone.repository;

import com.example.capstone.model.DogOwner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DogOwnerRepository extends MongoRepository<DogOwner, String> {
    Optional<DogOwner> findById(String id);
    Optional<DogOwner> findByEmail(String email); // Custom query method to find a DogOwner by email
}
