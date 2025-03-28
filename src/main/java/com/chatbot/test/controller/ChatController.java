package com.chatbot.test.controller;

import com.chatbot.test.dto.ChatRequestDTO;
import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.service.ChatService;
import com.chatbot.test.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/queries")
@RequiredArgsConstructor
@CrossOrigin
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatResponseDTO> askQuestion(@RequestBody ChatRequestDTO questionRequestDTO) {
        ChatResponseDTO answer = chatService.newchat(questionRequestDTO);
        return ResponseEntity.ok(answer);
    }
}
