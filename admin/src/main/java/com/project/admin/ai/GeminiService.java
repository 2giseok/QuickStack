package com.project.admin.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.admin.ai.dto.GeminiRequest;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class GeminiService implements AiService {

    private final ObjectMapper objectMapper;
    private final RestClient aiRestClient;
    private final PromptManager promptManager;


    @Value("${gemini.api.url}")
    private String baseUrl;

    @Value("${gemini.api.model}")
    private String model;

    private final String apiKey;

    public GeminiService(
            ObjectMapper objectMapper,
            RestClient aiRestClient,
            PromptManager promptManager,
            @Qualifier("geminiApiKey") String apiKey) {
        this.objectMapper = objectMapper;
        this.aiRestClient = aiRestClient;
        this.promptManager = promptManager;
        this.apiKey = apiKey;
    }

    @Retryable(
            includes = {HttpServerErrorException.class,
                    HttpClientErrorException.TooManyRequests.class},
            maxRetries = 2,
            delay = 1000,
            multiplier = 2.0,
            timeUnit = TimeUnit.MILLISECONDS

    )
    @Override
    public String summarize(String text) throws Exception {
        String fullPrompt = promptManager.buildPrompt(text);
        String requestBody = objectMapper.writeValueAsString(GeminiRequest.of(fullPrompt));

        String response = callApi(requestBody);
        return parseResponse(response);
    }

    private String callApi(String requestBody) throws Exception {
        return aiRestClient.post()
                .uri(baseUrl + model + ":generateContent")
                .header("x-goog-api-key", apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(String.class);
    }

    private String parseResponse(String response) throws Exception {
        JsonNode root = objectMapper.readTree(response);
        JsonNode candidates = root.path("candidates").get(0);
        if (candidates == null) {
            throw new IllegalStateException("Gemini response error" + response);
        }
        JsonNode part = candidates.path("content").path("parts").get(0);
        if (part == null) {
            throw new IllegalStateException("Gemini response error" + response);
        }
        return part.path("text").asText();
    }
}
