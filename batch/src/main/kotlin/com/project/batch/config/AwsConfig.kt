package com.project.batch.config

import com.project.batch.config.properties.DiscordProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider
import software.amazon.awssdk.auth.credentials.InstanceProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import tools.jackson.databind.ObjectMapper

@Configuration
class AwsConfig(
    @Value("\${aws.secrets.name.github-release-token}") private val githubTokenSecretName: String,
    private val discordProperties: DiscordProperties,
    private val objectMapper: ObjectMapper
) {

    @Bean
    fun githubReleaseDiscordWebhookUrl(secretsManagerClient: SecretsManagerClient): String {
        val secretId = discordProperties.channel["github-release-notification"]
            ?: throw IllegalArgumentException("Discord channel 'github-release-notification' is not configured")
        return getSecretValue(secretsManagerClient, secretId)
    }

    @Bean
    fun techblogDiscordWebhookUrl(secretsManagerClient: SecretsManagerClient): String {
        val secretId = discordProperties.channel["techblog-notification"]
            ?: throw IllegalArgumentException("Discord channel 'techblog-notification' is not configured")
        return getSecretValue(secretsManagerClient, secretId)
    }

    @Bean
    fun githubToken(secretsManagerClient: SecretsManagerClient): String {
        return getSecretValue(secretsManagerClient, githubTokenSecretName)
    }

    private fun getSecretValue(client: SecretsManagerClient, secretId: String): String {
        val secretString = client.getSecretValue(
            GetSecretValueRequest.builder().secretId(secretId).build()
        ).secretString()
        return objectMapper.readTree(secretString)[secretId]?.asString()
            ?: throw IllegalStateException("Secret '$secretId' 에 해당하는 값이 없습니다")
    }

    @Bean
    fun secretsManagerClient(awsCredentialsProvider: AwsCredentialsProvider): SecretsManagerClient = // 3. 파라미터로 주입
        SecretsManagerClient.builder()
            .credentialsProvider(awsCredentialsProvider)
            .region(Region.AP_NORTHEAST_2)
            .build()

    @Bean
    fun awsCredentialsProvider(): AwsCredentialsProvider =
        AwsCredentialsProviderChain.builder()
            .addCredentialsProvider(ProfileCredentialsProvider.create())
            .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
            .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.builder().build())
            .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
            .build()
}