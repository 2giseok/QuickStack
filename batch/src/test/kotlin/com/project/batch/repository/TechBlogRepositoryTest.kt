package com.project.batch.repository

import com.project.batch.config.MySqlTestContainer
import com.project.batch.constants.Region
import com.project.batch.constants.Source
import com.project.batch.constants.Status
import com.project.batch.fixture.TestFixtures
import io.mockk.mockk
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.runBlocking
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
import java.util.concurrent.atomic.AtomicLong

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.NONE,
    properties = [
        "spring.main.allow-bean-definition-overriding=true",
        "spring.sql.init.mode=never",
    ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TechBlogRepositoryTest {

    @TestConfiguration
    class TestAwsConfig {
        @Bean @Primary
        fun secretsManagerClient(): SecretsManagerClient = mockk(relaxed = true)

        @Bean("githubReleaseDiscordWebhookUrl") @Primary
        fun githubReleaseDiscordWebhookUrl(): String = "https://test-webhook/github"

        @Bean("techblogDiscordWebhookUrl") @Primary
        fun techblogDiscordWebhookUrl(): String = "https://test-webhook/techblog"

        @Bean("githubToken") @Primary
        fun githubToken(): String = "test-token"

        @Bean("githubWebClient") @Primary
        fun githubWebClient(): WebClient = mockk(relaxed = true)

        @Bean("commonWebClient") @Primary
        fun commonWebClient(): WebClient = mockk(relaxed = true)
    }

    @Autowired
    lateinit var repository: TechBlogRepository

    @Autowired
    lateinit var databaseClient: DatabaseClient

    private val idGenerator = AtomicLong(1000L)

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
        runBlocking {
            databaseClient.sql("""
                CREATE TABLE IF NOT EXISTS tech_blog (
                    id           BIGINT        NOT NULL PRIMARY KEY,
                    source       VARCHAR(50)   NOT NULL,
                    region       VARCHAR(20)   NOT NULL,
                    title        VARCHAR(255)  NULL,
                    url          VARCHAR(1000) NOT NULL,
                    published_at DATETIME(6)   NOT NULL,
                    tags         JSON          NULL,
                    status       VARCHAR(20)   NOT NULL DEFAULT 'PENDING',
                    created_at   DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
                    CONSTRAINT uq_tech_blog_url UNIQUE (url(768))
                )
            """.trimIndent()).then().awaitSingleOrNull()
        }
    }

    @BeforeEach
    fun setUp() {
        runBlocking {
            databaseClient.sql("TRUNCATE TABLE tech_blog").then().awaitSingleOrNull()
        }
        idGenerator.set(1000L)
    }

    @Test
    fun `신규 블로그 insert 시 PENDING 상태로 저장된다`() = runTest {
        repository.bulkInsert(listOf(techBlog()))

        val result = repository.selectBlogsPublishedToday(LocalDate.of(2026, 4, 19))
        assertThat(result[0].status).isEqualTo(Status.PENDING)
    }

    @Test
    fun `동일한 url로 재insert 시 기존 status가 유지된다`() = runTest {
        val url = "https://tech.kakao.com/2026/04/19/duplicate"
        repository.bulkInsert(listOf(techBlog(url = url)))
        databaseClient.sql("UPDATE tech_blog SET status = 'PUBLISHED' WHERE url = '$url'")
            .fetch().rowsUpdated().awaitSingle()

        repository.bulkInsert(listOf(techBlog(id = idGenerator.getAndIncrement(), url = url)))

        val result = repository.selectBlogsPublishedToday(LocalDate.of(2026, 4, 19))
        assertThat(result[0].status).isEqualTo(Status.PUBLISHED)
    }

    @Test
    fun `오늘 발행된 블로그만 조회된다`() = runTest {
        repository.bulkInsert(listOf(
            // 2026-04-19T00:00:00Z = 2026-04-19T09:00 KST → 포함
            techBlog(url = "https://a.com/1", publishedAt = Instant.parse("2026-04-19T00:00:00Z")),
            // 2026-04-18T14:59:59Z = 2026-04-18T23:59 KST → 미포함
            techBlog(url = "https://b.com/2", publishedAt = Instant.parse("2026-04-18T14:59:59Z")),
            // 2026-04-19T15:00:00Z = 2026-04-20T00:00 KST → 미포함
            techBlog(url = "https://c.com/3", publishedAt = Instant.parse("2026-04-19T15:00:00Z")),
        ))

        val result = repository.selectBlogsPublishedToday(LocalDate.of(2026, 4, 19))

        assertThat(result).hasSize(1)
        assertThat(result[0].url).isEqualTo("https://a.com/1")
    }

    @Test
    fun `오늘 발행된 블로그가 없으면 빈 리스트를 반환한다`() = runTest {
        repository.bulkInsert(listOf(
            techBlog(publishedAt = Instant.parse("2026-04-18T00:00:00Z")),
        ))

        val result = repository.selectBlogsPublishedToday(LocalDate.of(2026, 4, 19))

        assertThat(result).isEmpty()
    }

    private fun techBlog(
        id: Long = idGenerator.getAndIncrement(),
        source: String = Source.KAKAO_TECH.name,
        region: Region = Region.DOMESTIC,
        title: String = "테스트 블로그",
        url: String = "https://tech.kakao.com/2026/04/19/test-${id}",
        publishedAt: Instant = Instant.parse("2026-04-19T00:00:00Z"),
    ) = TestFixtures.techBlog(
        source = source,
        region = region,
        title = title,
        url = url,
        publishedAt = publishedAt,
    ).copy(id = id)
}
