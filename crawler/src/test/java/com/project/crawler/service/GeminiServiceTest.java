package com.project.crawler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;

import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@ExtendWith(MockitoExtension.class)
class GeminiServiceTest {

    private MockRestServiceServer mockServer;
    private GeminiService geminiService;


    @BeforeEach
    void setup() {

        RestClient.Builder builder = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(builder).build();
        geminiService = new GeminiService(
                builder.build(),
                "test-api-key",
                new ObjectMapper());
    }

    @Test
    void summary_success() throws Exception {
        String responseBody = """
                {
                 "candidates": [{
                 "content": {
                 "parts": [{"text": "## summary text"}]
                   }
                   }]
                   }
                """;


        mockServer.expect(requestTo(containsString("generateContent")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(responseBody, MediaType.APPLICATION_JSON));

        String result = geminiService.summarize("test text");

        assertThat(result).isEqualTo("## summary text");
    }

    @Test
    void summary_fail() {


        mockServer.expect(requestTo(containsString("generateContent")))
                .andRespond(withStatus(HttpStatus.SERVICE_UNAVAILABLE));


        assertThatThrownBy(() -> geminiService.summarize("test"))
                .isInstanceOf(HttpServerErrorException.class);
    }


}