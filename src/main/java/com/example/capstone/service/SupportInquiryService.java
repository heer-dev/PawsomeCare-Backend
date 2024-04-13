package com.example.capstone.service;

import com.example.capstone.model.SupportInquiry;
import com.example.capstone.repository.SupportInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupportInquiryService {
    private final SupportInquiryRepository repository;

    @Autowired
    public SupportInquiryService(SupportInquiryRepository repository) {
        this.repository = repository;
    }

    public SupportInquiry saveInquiry(SupportInquiry inquiry) {
        return repository.save(inquiry);
    }

    public List<SupportInquiry> getAllInquiries() {
        return repository.findAll();
    }

    public SupportInquiry getInquiryById(String id) {
        Optional<SupportInquiry> optionalInquiry = repository.findById(id);
        return optionalInquiry.orElse(null);
    }
}