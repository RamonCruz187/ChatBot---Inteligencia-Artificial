package com.chatbot.test.service;

import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.dto.ChatRequestDTO;
import com.chatbot.test.entity.Query;

import java.util.List;
import java.util.Optional;

public interface QueryService {

    ChatResponseDTO newQuery(ChatRequestDTO questionRequestDTO);

    ChatResponseDTO getQuery(Long queryId);

    Optional<List<Query>> getAllQueries(Long chatId);

    String askQuestion(Long businessId, String question);
}
