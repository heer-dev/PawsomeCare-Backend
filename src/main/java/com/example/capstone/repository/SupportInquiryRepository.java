package com.example.capstone.repository;

import com.example.capstone.model.SupportInquiry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SupportInquiryRepository extends MongoRepository<SupportInquiry, String> {
    // Custom query methods if needed


}
