package com.example.capstone.repository;

import com.example.capstone.model.DogOwner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DogOwnerRepository extends MongoRepository<DogOwner, String> {
    Optional<DogOwner> findById(String id);
    Optional<DogOwner>findByEmail(String email);
    Optional<DogOwner> findByResetToken(String resetToken);
    Stream<DogOwner> findByIdContainingOrFirstNameContainingOrLastNameContaining(String id, String firstName, String lastName);


}
