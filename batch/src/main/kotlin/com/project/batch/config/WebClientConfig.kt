package com.project.batch.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import tools.jackson.databind.ObjectMapper
import java.time.Duration
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig(
    @Value("\${aws.secrets.name.github-release-token}") private val secretName: String,
    private val secretsManagerClient: SecretsManagerClient,
    private val objectMapper: ObjectMapper,
) {

    companion object {
        private const val CONNECT_TIMEOUT_MILLIS = 5_000
        private const val READ_TIMEOUT_MILLIS = 10_000L
        private const val WRITE_TIMEOUT_MILLIS = 10_000L
        private const val GITHUB_BASE_URL = "https://api.github.com"
        private const val GITHUB_MAX_IN_MEMORY_SIZE = 10 * 1024 * 1024
    }

    @Bean(name = ["commonWebClient"])
    fun commonWebClient(): WebClient {
        val httpClient = HttpClient.create()
            .followRedirect(true)
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
            .responseTimeout(Duration.ofMillis(READ_TIMEOUT_MILLIS))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

    @Bean(name = ["githubWebClient"])
    fun githubWebClient(): WebClient {
        val token = objectMapper.readTree(
            secretsManagerClient.getSecretValue(
                GetSecretValueRequest.builder().secretId(secretName).build()
            ).secretString()
        ).get(secretName).asString()

        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
            .responseTimeout(Duration.ofMillis(READ_TIMEOUT_MILLIS))
            .doOnConnected { conn ->
                conn.addHandlerLast(ReadTimeoutHandler(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(WRITE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS))
            }

        return WebClient.builder()
            .baseUrl(GITHUB_BASE_URL)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .codecs { it.defaultCodecs().maxInMemorySize(GITHUB_MAX_IN_MEMORY_SIZE) }
            .defaultHeader("Authorization", "Bearer $token")
            .defaultHeader("Accept", "application/vnd.github+json")
            .defaultHeader("X-GitHub-Api-Version", "2022-11-28")
            .build()
    }
}