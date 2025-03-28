package com.chatbot.test.service.Impl;

import com.chatbot.test.OpenAiClient;
import com.chatbot.test.dto.ChatRequestDTO;
import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.entity.Business;
import com.chatbot.test.entity.Chat;
import com.chatbot.test.entity.Query;
import com.chatbot.test.repository.BusinessRepository;
import com.chatbot.test.repository.ChatRepository;
import com.chatbot.test.repository.QueryRepository;
import com.chatbot.test.service.ChatService;
import com.chatbot.test.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final QueryRepository queryRepository;
    private final BusinessRepository businessRepository;
    private final OpenAiClient openAiClient;
    private final QueryService queryService;
    private final ChatRepository chatRepository;


    @Override
    public ChatResponseDTO newchat(ChatRequestDTO chatRequestDTO) {

        //buscamos el negocio
        Business business = businessRepository.findById(chatRequestDTO.businessId()).orElseThrow(() -> new RuntimeException("Negocio no encontrado"));

        //buscamos el chat
        Chat chat=chatRepository.findChatByChatId(chatRequestDTO.chatId()).orElse(null);
        if(chat==null){
            System.out.println("chat es null");
            chat=new Chat();
            chat.setChatId(UUID.randomUUID().toString());
            chat.setBusiness(business);
            chatRepository.save(chat);
        }

        //buscamos las preguntas
        List<Query> responses = queryService.getAllQueries(chat.getId()).orElse(null);
        List<ChatResponseDTO> chatResponseDTOS = responses.stream().map(response -> new ChatResponseDTO(response.getQuestion(), response.getAnswer(), response.getChat().getChatId(), response.getTimestamp())).toList();
        System.out.println(chatResponseDTOS);

        //seteamos fecha y hora
        LocalDateTime now = LocalDateTime.now();
        String fechaYHora= "Hoy es "+ now.getDayOfWeek() + ", " + now.getDayOfMonth() + " de " + now.getMonth() + " de " + now.getYear() + " y son las " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();

        //llamar a OpenAI
        String context = "Ten en cuenta la fecha y hora actuales: "+ fechaYHora  + "Eres un asistente virtual de un negocio. Tu tarea es responder solo sobre el comercio y sus horarios. No debes responder sobre otros temas, como clima, eventos u otros negocios. Por último, ten muy en cuenta el historial de preguntas anteriores que te voy mandando, analizalas para que la respuesta que des, tenga relación con las preguntas anteriores. " +
                "Información del comercio: " + business.getInformation()+ "Historial de ultimas preguntas, tenlas en cuenta en el caso de que la pregunta actual esté relacionada con la pregunta anterior: " + chatResponseDTOS +
                " Si la pregunta no está relacionada con los productos o servicios, informa que no puedes responder sobre ese tema.";

        System.out.println(context);
        String answer = openAiClient.getResponseFronOpenAi(context, chatRequestDTO.question()).block();

        //guardar en la base de datos
        Query query = new Query();
        query.setQuestion(chatRequestDTO.question());
        query.setAnswer(answer);
        query.setTimestamp(LocalDateTime.now());
        chat.addQuery(query);
        queryRepository.save(query);

        return new ChatResponseDTO(chatRequestDTO.question(), answer,chat.getChatId(),query.getTimestamp());
    }
}
