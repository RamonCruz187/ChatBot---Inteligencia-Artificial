package com.chatbot.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient {
    private final WebClient webClient;

    @Value("${OPENAI_API_KEY}")
    private String OPENAI_API_KEY;

    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public OpenAiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(OPENAI_API_URL)
                .build();
    }

    public Mono<String> getResponseFronOpenAi(String context, String question) {
        //construcción del prompt
        String prompt = "Contexto del negocio"+context+"\nPregunta: "+question+"\nRespuesta:";

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "temperature", 0.5
        );
        return webClient.post()
                .uri(OPENAI_API_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        if (message != null && message.containsKey("content")) {
                            return Mono.just((String) message.get("content"));
                        }
                    }
                    return Mono.just("No se recibió una respuesta válida de OpenAI.");
                })
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)) // Reintenta 3 veces con espera exponencial
                        .filter(throwable -> throwable instanceof WebClientResponseException &&
                                ((WebClientResponseException) throwable).getStatusCode().value() == 429))
                .onErrorResume(e -> {
                    e.printStackTrace();
                    return Mono.just("Error al comunicarse con OpenAI: " + e.getMessage());
                });


    }
}
