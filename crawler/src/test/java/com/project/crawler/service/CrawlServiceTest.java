package com.project.crawler.service;

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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
class CrawlServiceTest {


    private MockRestServiceServer mockServer;
    CrawlService crawlService;


    @BeforeEach
    void setup() {

        RestClient.Builder builder = RestClient.builder();
        mockServer = MockRestServiceServer.bindTo(builder).build();
        crawlService = new CrawlService(builder.build());
    }

    @Test
    void crawling_success() {

        String url = "https://techblog.woowahan.com/26177/";
        String html = "<html><body><p>test</p></body></html>";

        mockServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(html, MediaType.TEXT_HTML));

        String result = crawlService.crawl(url);

        assertThat(result).isEqualTo("test");


    }

    @Test
    void crawling_fail() {

        String url = "https://techblog.woowahan.com/26177/";


        mockServer.expect(requestTo(url))
                .andRespond(withStatus(HttpStatus.GATEWAY_TIMEOUT));


        assertThatThrownBy(() -> crawlService.crawl(url)).isInstanceOf(HttpServerErrorException.class);


    }
}