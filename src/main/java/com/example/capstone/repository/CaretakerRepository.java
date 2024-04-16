package com.example.capstone.repository;

import com.example.capstone.model.Caretaker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface CaretakerRepository extends MongoRepository<Caretaker, String> {
    Optional<Caretaker> findById(String id);
    Optional<Caretaker> findByEmail(String email);
    Optional<Caretaker> findByResetToken(String resetToken);
    Stream<Caretaker> findByIdContainingOrFirstNameContainingOrLastNameContaining(String id, String firstName, String lastName);
}