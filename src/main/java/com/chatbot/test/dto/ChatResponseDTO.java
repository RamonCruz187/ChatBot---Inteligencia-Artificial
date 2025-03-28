package com.chatbot.test.dto;

import java.time.LocalDateTime;

public record ChatResponseDTO(String question, String answer, String chatId, LocalDateTime timestamp) {
}
