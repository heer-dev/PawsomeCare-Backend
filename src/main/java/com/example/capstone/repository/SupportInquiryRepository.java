package com.example.capstone.repository;

import com.example.capstone.model.SupportInquiry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupportInquiryRepository extends MongoRepository<SupportInquiry, String> {
    // Custom query methods if needed
}
