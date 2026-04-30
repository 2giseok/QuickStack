package com.project.batch.config

import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import java.time.Duration

class MySqlTestContainer {

    companion object {
        private const val MYSQL_IMAGE = "mysql:8.0"
        private const val DATABASE_NAME = "quick_stack"
        private const val USERNAME = "test"
        private const val PASSWORD = "test2024!"

        @JvmStatic
        val container: MySQLContainer<*> = MySQLContainer(MYSQL_IMAGE)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(USERNAME)
            .withPassword(PASSWORD)
            .waitingFor(Wait.forListeningPort())
            .withStartupTimeout(Duration.ofMinutes(2))
            .withLogConsumer { frame ->
                println("[MYSQL] ${frame.utf8String.trim()}")
            }

        @JvmStatic
        fun getHost(): String = container.host

        @JvmStatic
        fun getPort(): Int = container.getMappedPort(3306)

        @JvmStatic
        fun getUsername(): String = container.username

        @JvmStatic
        fun getPassword(): String = container.password

        @JvmStatic
        fun getDatabaseName(): String = container.databaseName

        @JvmStatic
        fun stop() {
            if (container.isRunning) container.stop()
        }
    }
}