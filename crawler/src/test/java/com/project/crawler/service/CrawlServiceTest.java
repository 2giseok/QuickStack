package com.project.crawler.service;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import static org.assertj.core.api.Assertions.*;
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
        RestClient restClient = builder.build();

        crawlService = new CrawlService(restClient);
    }

    @Test
    void 크롤링_성공(){

        String url = "https://example.com/post";
        String fakeHtml = "<html><body>테스트 본문입니다</body></html";
        MediaType mediaType = new MediaType("text", "html", StandardCharsets.UTF_8);

        mockServer.expect(requestTo(url))
                .andRespond(withSuccess(fakeHtml,mediaType));
        String result = crawlService.crawl(url);
        assertThat(result).isEqualTo("테스트 본문입니다");
        mockServer.verify();
    }

    @Test
    void 크롤링_실패(){
        String url = "https://example.com/error";
        mockServer.expect(requestTo(url))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThatThrownBy(() -> crawlService.crawl(url)).isInstanceOf(HttpServerErrorException.class);
        mockServer.verify();
    }
}