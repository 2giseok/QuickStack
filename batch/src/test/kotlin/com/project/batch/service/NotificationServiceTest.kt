package com.project.batch.service

import com.project.batch.constants.Source
import com.project.batch.constants.TechStack
import com.project.batch.fixture.TestFixtures
import com.project.batch.service.dto.DiscordWebhookRequest
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBodilessEntity
import java.time.LocalDate

class NotificationServiceTest {

    private val webClient = mockk<WebClient>()
    private val githubReleaseWebhookUrl = "https://discord.com/api/webhooks/github-test"
    private val techblogWebhookUrl = "https://discord.com/api/webhooks/techblog-test"
    private val service = NotificationService(githubReleaseWebhookUrl, techblogWebhookUrl, webClient)

    private val requestBodyUriSpec = mockk<WebClient.RequestBodyUriSpec>()
    private val requestBodySpec = mockk<WebClient.RequestBodySpec>()
    private val requestHeadersSpec = mockk<WebClient.RequestHeadersSpec<*>>()
    private val responseSpec = mockk<WebClient.ResponseSpec>()

    @BeforeEach
    fun setUp() {
        mockkStatic(WebClient.ResponseSpec::awaitBodilessEntity)
        every { webClient.post() } returns requestBodyUriSpec
        every { requestBodyUriSpec.uri(any<String>()) } returns requestBodySpec
        every { requestBodySpec.contentType(MediaType.APPLICATION_JSON) } returns requestBodySpec
        every { requestHeadersSpec.retrieve() } returns responseSpec
        every { responseSpec.onStatus(any(), any()) } returns responseSpec
        coEvery { responseSpec.awaitBodilessEntity() } returns mockk()
    }

    // ── sendReleaseNotification ───────────────────────────────────────────────

    @Test
    fun `embed 제목에 날짜와 릴리즈 건수가 포함된다`() = runTest {
        val slot = slot<Any>()
        every { requestBodySpec.bodyValue(capture(slot)) } returns requestHeadersSpec

        service.sendReleaseNotification(listOf(TestFixtures.githubRelease()), LocalDate.of(2026, 4, 19))

        val embed = (slot.captured as DiscordWebhookRequest).embeds[0]
        assertThat(embed.title).isEqualTo("🎉 2026-04-19 총 1건")
    }

    @Test
    fun `각 릴리즈가 기술스택 displayName과 태그명을 포함하는 필드로 변환된다`() = runTest {
        val slot = slot<Any>()
        every { requestBodySpec.bodyValue(capture(slot)) } returns requestHeadersSpec
        val releases = listOf(
            TestFixtures.githubRelease(techStack = TechStack.REACT, tagName = "v18.0.0"),
            TestFixtures.githubRelease(techStack = TechStack.SPRING_BOOT, tagName = "v3.0.0"),
        )

        service.sendReleaseNotification(releases, LocalDate.of(2026, 4, 19))

        val fields = (slot.captured as DiscordWebhookRequest).embeds[0].fields
        assertThat(fields).hasSize(2)
        assertThat(fields[0].name).isEqualTo(TechStack.REACT.displayName)
        assertThat(fields[0].value).contains("v18.0.0")
        assertThat(fields[1].name).isEqualTo(TechStack.SPRING_BOOT.displayName)
        assertThat(fields[1].value).contains("v3.0.0")
    }

    @Test
    fun `릴리즈 알림 발송 중 예외가 발생해도 전파되지 않는다`() = runTest {
        every { requestBodySpec.bodyValue(any()) } returns requestHeadersSpec
        coEvery { responseSpec.awaitBodilessEntity() } throws RuntimeException("Discord 연결 실패")

        assertThat(
            runCatching { service.sendReleaseNotification(listOf(TestFixtures.githubRelease()), LocalDate.of(2026, 4, 19)) }
        ).isInstanceOf(Result::class.java).matches { it.isSuccess }
    }

    // ── sendTechBlogNotification ──────────────────────────────────────────────

    @Test
    fun `embed 제목에 날짜와 블로그 건수가 포함된다`() = runTest {
        val slot = slot<Any>()
        every { requestBodySpec.bodyValue(capture(slot)) } returns requestHeadersSpec

        service.sendTechBlogNotification(listOf(TestFixtures.techBlog()), LocalDate.of(2026, 4, 19))

        val embed = (slot.captured as DiscordWebhookRequest).embeds[0]
        assertThat(embed.title).isEqualTo("📝 2026-04-19 기술 블로그 1건")
    }

    @Test
    fun `각 블로그가 source displayName과 제목을 포함하는 필드로 변환된다`() = runTest {
        val slot = slot<Any>()
        every { requestBodySpec.bodyValue(capture(slot)) } returns requestHeadersSpec
        val blogs = listOf(
            TestFixtures.techBlog(source = Source.KAKAO.name, title = "Kafka 도입기"),
            TestFixtures.techBlog(source = Source.WOOWAHAN.name, title = "Spring 최적화"),
        )

        service.sendTechBlogNotification(blogs, LocalDate.of(2026, 4, 19))

        val fields = (slot.captured as DiscordWebhookRequest).embeds[0].fields
        assertThat(fields).hasSize(2)
        assertThat(fields[0].name).isEqualTo(Source.KAKAO.displayName)
        assertThat(fields[0].value).contains("Kafka 도입기")
        assertThat(fields[1].name).isEqualTo(Source.WOOWAHAN.displayName)
        assertThat(fields[1].value).contains("Spring 최적화")
    }

    @Test
    fun `블로그 알림 발송 중 예외가 발생해도 전파되지 않는다`() = runTest {
        every { requestBodySpec.bodyValue(any()) } returns requestHeadersSpec
        coEvery { responseSpec.awaitBodilessEntity() } throws RuntimeException("Discord 연결 실패")

        assertThat(
            runCatching { service.sendTechBlogNotification(listOf(TestFixtures.techBlog()), LocalDate.of(2026, 4, 19)) }
        ).isInstanceOf(Result::class.java).matches { it.isSuccess }
    }

}
