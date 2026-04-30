package com.project.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.AwsCredentialsProviderChain
import software.amazon.awssdk.auth.credentials.ContainerCredentialsProvider
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider
import software.amazon.awssdk.auth.credentials.WebIdentityTokenFileCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient

@Configuration
class AwsConfig {

    @Bean
    fun awsCredentialsProvider(): AwsCredentialsProvider =
        AwsCredentialsProviderChain.builder()
            .addCredentialsProvider(ProfileCredentialsProvider.create())
            .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
            .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.builder().build())
            .build()

    @Bean
    fun secretsManagerClient(awsCredentialsProvider: AwsCredentialsProvider): SecretsManagerClient =
        SecretsManagerClient.builder()
            .region(Region.AP_NORTHEAST_2)
            .credentialsProvider(awsCredentialsProvider)
            .build()
}
