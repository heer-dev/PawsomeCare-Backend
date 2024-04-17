package com.example.capstone.controller;

import com.example.capstone.dto.SupportInquiryDTO;
import com.example.capstone.model.SupportInquiry;
import com.example.capstone.service.SupportInquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Map;


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
        inquiry.setDate(LocalDate.now());
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
        Optional<SupportInquiry> optionalInquiry = service.getInquiryById(id);
        return optionalInquiry
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/admin")
    public ResponseEntity<List<SupportInquiryDTO>> getAllInquiriesForAdmin() {
        List<SupportInquiryDTO> inquiries = service.getAllInquiriesDTO();
        return ResponseEntity.ok(inquiries);
    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<SupportInquiry> updateInquiryStatus(@PathVariable String id, @RequestBody Map<String, String> updates) {
        return service.getInquiryById(id)
                .map(inquiry -> {
                    inquiry.setStatus(updates.get("status"));
                    SupportInquiry updatedInquiry = service.saveInquiry(inquiry);
                    return ResponseEntity.ok(updatedInquiry);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


}
