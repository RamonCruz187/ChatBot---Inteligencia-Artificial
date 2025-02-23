package com.chatbot.test.mapper;

import com.chatbot.test.dto.BusinessRequestDTO;
import com.chatbot.test.dto.BusinessResponseDTO;
import com.chatbot.test.entity.Business;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BusinessMapper {

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "information", source = "information")
    })
    Business toBusiness(BusinessRequestDTO businessRequestDTO);

    BusinessResponseDTO toBusinessResponseDTO(Business business);

    List<BusinessResponseDTO> toBusinessResponseDTOs(List<Business> businesses);
}
