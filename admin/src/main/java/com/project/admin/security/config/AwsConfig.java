package com.project.admin.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;

@Configuration
public class AwsConfig {

    @Value("${aws.secrets.name.gemini-api-key}")
    private String geminiSecretName;

    @Bean
    public SecretsManagerClient secretsManagerClient() {
        return SecretsManagerClient.builder()
                .region(Region.AP_NORTHEAST_2)
                .credentialsProvider(DefaultCredentialsProvider.create())
                .build();
    }

    @Bean
    public String geminiApiKey(SecretsManagerClient secretsManagerClient) {
        return secretsManagerClient
                .getSecretValue(
                        GetSecretValueRequest.builder()
                                .secretId(geminiSecretName)
                                .build()
                ).secretString();
    }
}
