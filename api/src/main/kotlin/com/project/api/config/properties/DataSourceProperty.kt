package com.project.api.config.properties

data class DataSourceProperty(
    val username: String,
    val password: String,
    val engine: String,
    val host: String,
    val port: Int,
    val dbname: String,
    val dbInstanceIdentifier: String,
) {
    fun toJdbcUrl(): String =
        "jdbc:mysql://$host:$port/$dbname?useSSL=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8"
}