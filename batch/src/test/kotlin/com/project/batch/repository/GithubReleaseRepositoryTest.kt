package com.project.batch.repository

import com.project.batch.config.MySqlTestContainer
import com.project.batch.constants.Status
import com.project.batch.domain.GithubRelease
import io.mockk.mockk
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.web.reactive.function.client.WebClient
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient
import java.time.Instant
import java.time.LocalDate

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        "spring.main.allow-bean-definition-overriding=true",
        "spring.sql.init.mode=never",
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GithubReleaseRepositoryTest {

    @TestConfiguration
    class TestAwsConfig {
        @Bean @Primary
        fun secretsManagerClient(): SecretsManagerClient = mockk(relaxed = true)

        @Bean("discordWebhookUrl") @Primary
        fun discordWebhookUrl(): String = "https://test-webhook"

        @Bean("githubToken") @Primary
        fun githubToken(): String = "test-token"

        @Bean("githubWebClient") @Primary
        fun githubWebClient(): WebClient = mockk(relaxed = true)

        @Bean("commonWebClient") @Primary
        fun commonWebClient(): WebClient = mockk(relaxed = true)
    }

    @Autowired
    lateinit var repository: GithubReleaseRepository

    @Autowired
    lateinit var databaseClient: DatabaseClient

    companion object {
        init {
            MySqlTestContainer.container.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun overrideProperties(registry: DynamicPropertyRegistry) {
            registry.add("r2dbc.host") { MySqlTestContainer.getHost() }
            registry.add("r2dbc.port") { MySqlTestContainer.getPort() }
            registry.add("r2dbc.database") { MySqlTestContainer.getDatabaseName() }
            registry.add("r2dbc.username") { MySqlTestContainer.getUsername() }
            registry.add("r2dbc.password") { MySqlTestContainer.getPassword() }
            registry.add("aws.secrets.name.github-release-token") { "test/github-token" }
            registry.add("discord.channel.github-release-notification") { "test/discord-webhook" }
        }
    }

    @BeforeAll
    fun createTable() {
        databaseClient.sql("""
            CREATE TABLE IF NOT EXISTS github_release (
                tech_stack   VARCHAR(100) NOT NULL,
                tag_name     VARCHAR(100) NOT NULL,
                name         VARCHAR(500) NULL,
                body         MEDIUMTEXT   NULL,
                published_at DATETIME(6)  NOT NULL,
                prerelease   TINYINT(1)   NOT NULL DEFAULT 0,
                draft        TINYINT(1)   NOT NULL DEFAULT 0,
                status       VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
                PRIMARY KEY (tech_stack, tag_name)
            )
        """.trimIndent()).then().block()
    }

    @BeforeEach
    fun setUp() {
        databaseClient.sql("TRUNCATE TABLE github_release").then().block()
    }

    @Test
    fun `신규 릴리즈 insert 시 PENDING 상태로 저장된다`() = runTest {
        repository.bulkInsert(listOf(githubRelease(techStack = "REACT", tagName = "v18.0.0")))

        val result = repository.selectReleaseTodayPublished(LocalDate.of(2026, 4, 19))
        assertThat(result[0].status).isEqualTo(Status.PENDING)
    }

    @Test
    fun `동일한 복합키로 재insert 시 기존 status가 유지된다`() = runTest {
        repository.bulkInsert(listOf(githubRelease(techStack = "REACT", tagName = "v18.0.0")))
        databaseClient.sql("UPDATE github_release SET status = 'PUBLISHED' WHERE tech_stack = 'REACT' AND tag_name = 'v18.0.0'")
            .fetch().rowsUpdated().awaitSingle()

        repository.bulkInsert(listOf(githubRelease(techStack = "REACT", tagName = "v18.0.0")))

        val result = repository.selectReleaseTodayPublished(LocalDate.of(2026, 4, 19))
        assertThat(result[0].status).isEqualTo(Status.PUBLISHED)
    }

    @Test
    fun `오늘 발행된 릴리즈만 조회된다`() = runTest {
        repository.bulkInsert(listOf(
            // 2026-04-19T00:00:00Z = 2026-04-19T09:00 KST → 포함
            githubRelease("REACT", "v18.0.0", publishedAt = Instant.parse("2026-04-19T00:00:00Z")),
            // 2026-04-18T14:59:59Z = 2026-04-18T23:59 KST → 미포함
            githubRelease("NEXT_JS", "v14.0.0", publishedAt = Instant.parse("2026-04-18T14:59:59Z")),
            // 2026-04-19T15:00:00Z = 2026-04-20T00:00 KST → 미포함
            githubRelease("VUE", "v3.0.0", publishedAt = Instant.parse("2026-04-19T15:00:00Z")),
        ))

        val result = repository.selectReleaseTodayPublished(LocalDate.of(2026, 4, 19))

        assertThat(result).hasSize(1)
        assertThat(result[0].techStack).isEqualTo("REACT")
    }

    @Test
    fun `오늘 발행된 릴리즈가 없으면 빈 리스트를 반환한다`() = runTest {
        repository.bulkInsert(listOf(
            githubRelease("REACT", "v18.0.0", publishedAt = Instant.parse("2026-04-18T00:00:00Z")),
        ))

        val result = repository.selectReleaseTodayPublished(LocalDate.of(2026, 4, 19))

        assertThat(result).isEmpty()
    }

    private fun githubRelease(
        techStack: String = "REACT",
        tagName: String = "v1.0.0",
        publishedAt: Instant = Instant.parse("2026-04-19T00:00:00Z"),
    ) = GithubRelease(
        techStack = techStack,
        tagName = tagName,
        name = null,
        body = null,
        publishedAt = publishedAt,
        prerelease = false,
        draft = false,
    )
}