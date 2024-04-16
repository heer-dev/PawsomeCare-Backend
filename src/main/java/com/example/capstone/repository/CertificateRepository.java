package com.example.capstone.repository;

import com.example.capstone.model.Certificate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CertificateRepository extends MongoRepository<Certificate, String> {
    // Additional query methods can be defined here
}
