package com.chatbot.test.service.Impl;

import com.chatbot.test.OpenAiClient;
import com.chatbot.test.dto.ChatResponseDTO;
import com.chatbot.test.dto.QuestionRequestDTO;
import com.chatbot.test.entity.Business;
import com.chatbot.test.entity.Query;
import com.chatbot.test.mapper.QueryMapper;
import com.chatbot.test.repository.BusinessRepository;
import com.chatbot.test.repository.QueryRepository;
import com.chatbot.test.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryServiceImpl implements QueryService {

    private final QueryRepository queryRepository;
    private final BusinessRepository businessRepository;
    private final OpenAiClient openAiClient;


    @Override
    public ChatResponseDTO newQuery(QuestionRequestDTO questionRequestDTO) {
        Business business = businessRepository.findById(questionRequestDTO.businessId()).orElse(null);
        return null;
    }

    @Override
    public ChatResponseDTO getQuery(Long queryId) {
        return null;
    }

    @Override
    public List<ChatResponseDTO> getAllQueries(Long businessId) {
        return List.of();
    }

    @Override
    public String askQuestion(Long businessId, String question) {
       Business business = businessRepository.findById(businessId).orElseThrow(() -> new RuntimeException("Business not found"));

       //llamar a OpenAI
        LocalDateTime now = LocalDateTime.now();
        String fechaYHora= "Hoy es "+ now.getDayOfWeek() + ", " + now.getDayOfMonth() + " de " + now.getMonth() + " de " + now.getYear() + " y son las " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
        System.out.println(fechaYHora);
        String context = "Ten ene cuenta la fecha y hora actuales: "+ fechaYHora  + "Eres un asistente virtual de un negocio. Tu tarea es responder solo sobre el comercio y sus horarios. No debes responder sobre otros temas, como clima, eventos u otros negocios. " +
                "Información del comercio: " + business.getInformation()+
                " Si la pregunta no está relacionada con los productos o servicios, informa que no puedes responder sobre ese tema.";

        String answer = openAiClient.getResponseFronOpenAi(context, question).block();

        //guardar en la base de datos
        Query query = new Query();
        query.setBusiness(business);
        query.setQuestion(question);
        query.setAnswer(answer);
        query.setTimestamp(LocalDateTime.now());
        queryRepository.save(query);

        return answer;
    }
}
