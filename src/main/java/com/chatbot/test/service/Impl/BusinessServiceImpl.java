package com.chatbot.test.service.Impl;

import com.chatbot.test.dto.BusinessRequestDTO;
import com.chatbot.test.dto.BusinessResponseDTO;
import com.chatbot.test.entity.Business;
import com.chatbot.test.mapper.BusinessMapper;
import com.chatbot.test.repository.BusinessRepository;
import com.chatbot.test.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;



    @Override
    public BusinessResponseDTO newBusiness(BusinessRequestDTO businessRequestDTO) {
        System.out.println(businessRequestDTO.name());
        BusinessMapper businessMapper = Mappers.getMapper(BusinessMapper.class);
        Business business = businessRepository.save(businessMapper.toBusiness(businessRequestDTO));
        System.out.println(business.getName());
        return businessMapper.toBusinessResponseDTO(business);
    }

    @Override
    public BusinessResponseDTO getBusiness(Long businessId) {
        BusinessMapper businessMapper = Mappers.getMapper(BusinessMapper.class);
        return businessMapper.toBusinessResponseDTO(businessRepository.findById(businessId).orElse(null));
    }


    @Override
    public List<BusinessResponseDTO> getAllBusinesses() {
        BusinessMapper businessMapper = Mappers.getMapper(BusinessMapper.class);
        return businessMapper.toBusinessResponseDTOs(businessRepository.findAll());
    }
}
