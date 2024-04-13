package com.example.capstone.controller;

import com.example.capstone.model.SupportInquiry;
import com.example.capstone.service.SupportInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/support")
public class SupportInquiryController {

    private final SupportInquiryService service;

    @Autowired
    public SupportInquiryController(SupportInquiryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<SupportInquiry> submitInquiry(@RequestBody @Valid SupportInquiry inquiry) {
        SupportInquiry savedInquiry = service.saveInquiry(inquiry);
        return ResponseEntity.ok(savedInquiry);
    }

    @GetMapping
    public ResponseEntity<List<SupportInquiry>> getAllInquiries() {
        List<SupportInquiry> inquiries = service.getAllInquiries();
        return ResponseEntity.ok(inquiries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupportInquiry> getInquiryById(@PathVariable String id) {
        SupportInquiry inquiry = service.getInquiryById(id);
        if (inquiry != null) {
            return ResponseEntity.ok(inquiry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
