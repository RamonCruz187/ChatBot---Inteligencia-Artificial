package com.chatbot.test.service.Impl;

import com.chatbot.test.OpenAiClient;
import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.dto.ChatRequestDTO;
import com.chatbot.test.entity.Business;
import com.chatbot.test.entity.Chat;
import com.chatbot.test.entity.Query;
import com.chatbot.test.repository.BusinessRepository;
import com.chatbot.test.repository.QueryRepository;
import com.chatbot.test.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final QueryRepository queryRepository;
    private final BusinessRepository businessRepository;
    private final OpenAiClient openAiClient;



    @Override
    public ChatResponseDTO newQuery(ChatRequestDTO questionRequestDTO) {
        Business business = businessRepository.findById(questionRequestDTO.businessId()).orElse(null);
        return null;
    }

    @Override
    public ChatResponseDTO getQuery(Long queryId) {
        return null;
    }

    @Override
    public Optional<List<Query>>getAllQueries(Long chatId) {
        return queryRepository.findAllByChatId(chatId);
    }

    @Override
    public String askQuestion(Long businessId, String question) {
        return null;
    }
}
