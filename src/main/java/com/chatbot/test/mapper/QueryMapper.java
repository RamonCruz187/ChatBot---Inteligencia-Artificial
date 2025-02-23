package com.chatbot.test.mapper;

import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.entity.Query;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface QueryMapper {

    ChatResponseDTO toChatResponseDTO(Query query);
}
