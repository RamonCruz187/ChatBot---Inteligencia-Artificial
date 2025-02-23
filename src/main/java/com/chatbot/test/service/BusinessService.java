package com.chatbot.test.service;

import com.chatbot.test.dto.BusinessRequestDTO;
import com.chatbot.test.dto.BusinessResponseDTO;

import java.util.List;

public interface BusinessService {
    BusinessResponseDTO newBusiness(BusinessRequestDTO businessRequestDTO);

    BusinessResponseDTO getBusiness(Long businessId);

    List<BusinessResponseDTO> getAllBusinesses();

}
