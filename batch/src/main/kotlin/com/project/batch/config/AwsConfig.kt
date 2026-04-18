package com.project.batch.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest

@Configuration
class AwsConfig(
    @Value("\${aws.secrets.name.github-release-token}") private val githubTokenSecretName: String,
    @Value("\${discord.channel.github-release-notification}") private val discordWebhookSecretName: String,
) {

    @Bean
    fun discordWebhookUrl(): String =
        secretsManagerClient().getSecretValue(
            GetSecretValueRequest.builder().secretId(discordWebhookSecretName).build()
        ).secretString()

    @Bean
    fun githubToken(): String =
        secretsManagerClient().getSecretValue(
            GetSecretValueRequest.builder().secretId(githubTokenSecretName).build()
        ).secretString()

    @Bean
    fun secretsManagerClient(): SecretsManagerClient =
        SecretsManagerClient.builder()
            .credentialsProvider(awsCredentialsProvider())
            .region(Region.AP_NORTHEAST_2)
            .build()

    @Bean
    fun awsCredentialsProvider(): AwsCredentialsProvider =
        AwsCredentialsProviderChain.builder()
            .addCredentialsProvider(ProfileCredentialsProvider.create())
            .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
            .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.builder().build())
            .build()
}