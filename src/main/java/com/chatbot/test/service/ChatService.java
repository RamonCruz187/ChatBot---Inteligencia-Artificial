package com.chatbot.test.service;

import com.chatbot.test.dto.ChatRequestDTO;
import com.chatbot.test.dto.ChatResponseDTO;

public interface ChatService {

    ChatResponseDTO newchat(ChatRequestDTO chatRequestDTO);
}
