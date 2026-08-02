package com.project.admin.ai;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException.TooManyRequests;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.client.RestClient;

class GeminiServiceTest {

    private MockRestServiceServer mockServer;
    private  ObjectMapper objectMapper;
    private  PromptManager promptManager;
    private GeminiService geminiService;
    private final String url = "https://example.com/post/gemini-pro:generateContent";

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        promptManager = mock(PromptManager.class);

        RestClient.Builder builder = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(builder).build();
        RestClient restClient = builder.build();

        geminiService = new GeminiService(objectMapper,restClient,promptManager,"api-key");
        ReflectionTestUtils.setField(geminiService,"baseUrl","https://example.com/post/");
        ReflectionTestUtils.setField(geminiService,"model","gemini-pro");

        when(promptManager.buildPrompt(anyString())).thenReturn("프롬포트");

    }
    private String successBody(String text) {
        return """
                {"candidates":[{"content":{"parts":[{"text":"%s"}]}}]}
                """.formatted(text);

    }
    @Test
    void 성공() throws Exception {
        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(successBody("요약결과"), MediaType.APPLICATION_JSON));
        String result = geminiService.summarize("원본 텍스트");

        assertThat(result).isEqualTo("요약결과");
    }


    @Test
    void _500_실패() throws Exception {
        mockServer.expect(requestTo(url)).andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));


        assertThatThrownBy(() ->geminiService.summarize("원본 텍스트"))
                .isInstanceOf(HttpServerErrorException.class);

    }

    @Test
    void _429_실패() throws Exception {
        mockServer.expect(requestTo(url)).andRespond(withStatus(HttpStatus.TOO_MANY_REQUESTS));

        assertThatThrownBy(() ->geminiService.summarize("원본 텍스트"))
                .isInstanceOf(TooManyRequests.class);

    }
    @Test
    void 잘못된_반환_서식() throws  Exception {
        mockServer.expect(requestTo(url))
                .andRespond(withSuccess("{}", MediaType.APPLICATION_JSON));

        assertThatThrownBy(() ->geminiService.summarize("원본 텍스트"))
                .isInstanceOf(IllegalStateException.class);
    }



}
