package com.chatbot.test.controller;

import com.chatbot.test.dto.QuestionRequestDTO;
import com.chatbot.test.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queries")
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @PostMapping
    public ResponseEntity<String> askQuestion(@RequestBody QuestionRequestDTO questionRequestDTO) {
        String answer = queryService.askQuestion(questionRequestDTO.businessId(), questionRequestDTO.question());
        return ResponseEntity.ok(answer);
    }
}
