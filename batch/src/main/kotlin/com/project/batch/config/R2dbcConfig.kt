package com.project.batch.config

import io.asyncer.r2dbc.mysql.MySqlConnectionConfiguration
import io.asyncer.r2dbc.mysql.MySqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration

@Configuration
class R2dbcConfig(
    private val r2dbcProperty: R2dbcProperty
) : AbstractR2dbcConfiguration() {

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return MySqlConnectionFactory.from(
            MySqlConnectionConfiguration.builder()
                .host(r2dbcProperty.host)
                .port(r2dbcProperty.port)
                .database(r2dbcProperty.database)
                .username(r2dbcProperty.username)
                .password(r2dbcProperty.password)
                .build()
        )
    }

}