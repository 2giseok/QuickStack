package com.project.batch.service.dto

data class DiscordWebhookRequest(val embeds: List<DiscordEmbed>)

data class DiscordEmbed(
    val title: String,
    val fields: List<DiscordEmbedField>,
)

data class DiscordEmbedField(
    val name: String,
    val value: String,
    val inline: Boolean = false,
)
