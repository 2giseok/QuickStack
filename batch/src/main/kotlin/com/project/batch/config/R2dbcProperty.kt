package com.project.batch.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "r2dbc")
class R2dbcProperty @ConstructorBinding constructor(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val database: String
)