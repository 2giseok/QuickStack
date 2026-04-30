package com.project.batch.service

import com.project.batch.constants.TechStack
import com.project.batch.domain.GithubRelease
import com.project.batch.service.dto.DiscordEmbed
import com.project.batch.service.dto.DiscordEmbedField
import com.project.batch.service.dto.DiscordWebhookRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity
import org.springframework.web.reactive.function.client.bodyToMono
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Service
class NotificationService(
    @Qualifier("discordWebhookUrl") private val webhookUrl: String,
    @Qualifier("commonWebClient") private val client: WebClient,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(NotificationService::class.java)
        private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.of("Asia/Seoul"))
    }

    suspend fun sendReleaseNotification(releases: List<GithubRelease>, date: LocalDate) {
        try {
            val today = date.format(dateFormatter)
            val fields = releases.map { release ->
                val techStack = TechStack.valueOf(release.techStack)
                DiscordEmbedField(
                    name = techStack.displayName,
                    value = "[${release.tagName}](https://github.com/${techStack.owner}/${techStack.repo}/releases/tag/${release.tagName}) · ${dateTimeFormatter.format(release.publishedAt)}",
                )
            }
            val embed = DiscordEmbed(
                title = "🎉 $today 총 ${releases.size}건",
                fields = fields,
            )

            client.post()
                .uri(webhookUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(DiscordWebhookRequest(embeds = listOf(embed)))
                .retrieve()
                .onStatus({ it.is4xxClientError }) { response ->
                    response.bodyToMono<String>()
                        .map { body -> RuntimeException("Discord error: $body") }
                }
                .awaitBodilessEntity()
        } catch (e: Exception) {
            logger.error("Failed to send release notification", e)
        }
    }

}