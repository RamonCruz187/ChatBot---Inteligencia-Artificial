package com.chatbot.test.service;

import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.dto.QuestionRequestDTO;

import java.util.List;

public interface QueryService {

    ChatResponseDTO newQuery(QuestionRequestDTO questionRequestDTO);

    ChatResponseDTO getQuery(Long queryId);

    List<ChatResponseDTO> getAllQueries(Long businessId);

    String askQuestion(Long businessId, String question);
}
