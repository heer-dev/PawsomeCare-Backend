package com.example.capstone.service;

import com.example.capstone.dto.SupportInquiryDTO;
import com.example.capstone.model.SupportInquiry;
import com.example.capstone.repository.SupportInquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // This method correctly returns an Optional<SupportInquiry>
    public Optional<SupportInquiry> getInquiryById(String id) {
        return repository.findById(id);
    }

    public List<SupportInquiryDTO> getAllInquiriesDTO() {
        return repository.findAll().stream()
                .map(inquiry -> new SupportInquiryDTO(
                        inquiry.getId(),
                        inquiry.getEmail(),
                        inquiry.getDate(),
                        inquiry.getInquiry()))
                .collect(Collectors.toList());
    }
}
