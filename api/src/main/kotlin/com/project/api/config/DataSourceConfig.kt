package com.project.api.config

import com.project.api.config.properties.DataSourceProperty
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest
import tools.jackson.databind.ObjectMapper
import javax.sql.DataSource

@Configuration
@Profile("prod")
class DataSourceConfig(
    private val secretsManagerClient: SecretsManagerClient,
    private val objectMapper: ObjectMapper,
    @Value("\${aws.secretsmanager.database-endpoint}") private val secretName: String,
) {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    fun hikariConfig(): HikariConfig = HikariConfig()

    @Bean
    fun dataSource(hikariConfig: HikariConfig): DataSource {
        val secret = getSecretValue()
        return HikariDataSource(
            hikariConfig.apply {
                jdbcUrl = secret.toJdbcUrl()
                username = secret.username
                password = secret.password
            }
        )
    }

    private fun getSecretValue(): DataSourceProperty {
        val request = GetSecretValueRequest.builder()
            .secretId(secretName)
            .build()
        val secretString = secretsManagerClient.getSecretValue(request).secretString()
        return objectMapper.readValue(secretString, DataSourceProperty::class.java)
    }
}
