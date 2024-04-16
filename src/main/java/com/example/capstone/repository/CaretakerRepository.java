package com.example.capstone.repository;

import com.example.capstone.model.Caretaker;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CaretakerRepository extends MongoRepository<Caretaker, String> {
    Optional<Caretaker> findById(String id);
    Optional<Caretaker> findByEmail(String email);
    Optional<Caretaker> findByResetToken(String resetToken);
}