package com.project.batch.remote

import com.project.batch.remote.constants.GithubReleaseUri.GITHUB_RELEASES_URL
import com.project.batch.remote.dto.GithubReleaseResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component
class GithubReleaseApiCaller(
    @Qualifier("githubWebClient") private val client: WebClient,
) {
    suspend fun callAllReleases(owner: String, repo: String): List<GithubReleaseResponse> =
        client.get()
            .uri(GITHUB_RELEASES_URL, owner, repo)
            .retrieve()
            .onStatus({ it.isError }) { response ->
                response.createError()
            }
            .awaitBody<List<GithubReleaseResponse>>()
}