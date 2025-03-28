package com.chatbot.test.repository;

import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.entity.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QueryRepository extends JpaRepository<Query, Long> {
    Optional<List<Query>> findAllByChatId(Long chatId);
}
