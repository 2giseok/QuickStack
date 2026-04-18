package com.project.batch.remote.utils

import com.project.batch.remote.constants.GithubReleaseUri.BASE_URL
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient

@Component
class GithubReleaseClientGenerator(
    @Qualifier("githubToken") private val token: String,
) {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val ACCEPT_HEADER = "Accept"
        private const val X_GITHUB_API_VERSION_HEADER = "X-GITHUB-API-VERSION"
    }

    fun generate(): WebClient = WebClient.builder()
        .baseUrl(BASE_URL)
        .clientConnector(ReactorClientHttpConnector(HttpClient.create().responseTimeout(java.time.Duration.ofSeconds(30))))
        .codecs { it.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) }
        .defaultHeader(AUTHORIZATION_HEADER, "Bearer $token")
        .defaultHeader(ACCEPT_HEADER, "application/vnd.github+json")
        .defaultHeader(X_GITHUB_API_VERSION_HEADER, "2022-11-28")
        .build()
}