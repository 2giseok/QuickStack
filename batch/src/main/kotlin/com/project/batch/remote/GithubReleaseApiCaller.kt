package com.project.batch.remote

import com.project.batch.remote.constants.GithubReleaseUri.GITHUB_RELEASES_URL
import com.project.batch.remote.dto.GithubReleaseResponse
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.util.retry.Retry
import java.time.Duration

@Component
class GithubReleaseApiCaller(
    @Qualifier("githubWebClient") private val client: WebClient,
) {
    companion object {
        private const val MAX_RETRY = 3L
        private val RETRY_DELAY = Duration.ofSeconds(2)
    }

    suspend fun callAllReleases(owner: String, repo: String): List<GithubReleaseResponse> =
        client.get()
            .uri(GITHUB_RELEASES_URL, owner, repo)
            .retrieve()
            .onStatus({ it.isError }) { response ->
                response.createError()
            }
            .bodyToMono<List<GithubReleaseResponse>>()
            .retryWhen(Retry.fixedDelay(MAX_RETRY, RETRY_DELAY))
            .awaitSingle()
}