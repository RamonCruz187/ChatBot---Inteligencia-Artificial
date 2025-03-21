package com.chatbot.test.controller;

import com.chatbot.test.dto.BusinessRequestDTO;
import com.chatbot.test.dto.BusinessResponseDTO;
import com.chatbot.test.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business")
@RequiredArgsConstructor
@CrossOrigin
public class BusinessController {

    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<BusinessResponseDTO> newBusiness(@RequestBody BusinessRequestDTO businessRequestDTO) {
        return ResponseEntity.ok(businessService.newBusiness(businessRequestDTO));
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<BusinessResponseDTO> getBusiness(@PathVariable Long businessId) {
        return ResponseEntity.ok(businessService.getBusiness(businessId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BusinessResponseDTO>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusinesses());
    }
}
