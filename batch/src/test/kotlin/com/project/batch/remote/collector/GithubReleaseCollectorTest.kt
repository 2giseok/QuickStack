package com.project.batch.remote.collector

import com.project.batch.constants.Status
import com.project.batch.constants.TechStack
import com.project.batch.remote.GithubReleaseApiCaller
import com.project.batch.remote.dto.GithubReleaseResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class GithubReleaseCollectorTest {

    private val apiCaller = mockk<GithubReleaseApiCaller>()
    private val collector = GithubReleaseCollector(apiCaller)

    @Test
    fun `API 호출 성공 시 GithubRelease로 올바르게 변환된다`() = runTest {
        val techStack = TechStack.REACT
        val response = githubReleaseResponse(tagName = "v18.0.0")
        coEvery { apiCaller.callAllReleases(techStack.owner, techStack.repo) } returns listOf(response)

        val result = collector.collectAllReleases(listOf(techStack))

        assertThat(result).hasSize(1)
        assertThat(result[0].techStack).isEqualTo(techStack.name)
        assertThat(result[0].tagName).isEqualTo("v18.0.0")
        assertThat(result[0].prerelease).isFalse()
        assertThat(result[0].draft).isFalse()
        assertThat(result[0].status).isEqualTo(Status.PENDING)
    }

    @Test
    fun `수집된 릴리즈는 PENDING 상태로 초기화된다`() = runTest {
        val techStack = TechStack.REACT
        coEvery { apiCaller.callAllReleases(techStack.owner, techStack.repo) } returns listOf(
            githubReleaseResponse(tagName = "v18.0.0"),
            githubReleaseResponse(tagName = "v17.0.0"),
        )

        val result = collector.collectAllReleases(listOf(techStack))

        assertThat(result).allMatch { it.status == Status.PENDING }
    }

    @Test
    fun `API 호출 실패 시 해당 스택은 빈 리스트로 처리되고 나머지는 정상 수집된다`() = runTest {
        val failStack = TechStack.REACT
        val successStack = TechStack.NEXT_JS
        coEvery { apiCaller.callAllReleases(failStack.owner, failStack.repo) } throws RuntimeException("API Error")
        coEvery { apiCaller.callAllReleases(successStack.owner, successStack.repo) } returns listOf(githubReleaseResponse())

        val result = collector.collectAllReleases(listOf(failStack, successStack))

        assertThat(result).hasSize(1)
        assertThat(result[0].techStack).isEqualTo(successStack.name)
    }

    @Test
    fun `모든 API 호출 실패 시 빈 리스트를 반환한다`() = runTest {
        coEvery { apiCaller.callAllReleases(any(), any()) } throws RuntimeException("API Error")

        val result = collector.collectAllReleases(listOf(TechStack.REACT, TechStack.NEXT_JS))

        assertThat(result).isEmpty()
    }

    @Test
    fun `여러 스택의 릴리즈를 하나의 리스트로 합쳐서 반환한다`() = runTest {
        val stack1 = TechStack.REACT
        val stack2 = TechStack.NEXT_JS
        coEvery { apiCaller.callAllReleases(stack1.owner, stack1.repo) } returns listOf(
            githubReleaseResponse(tagName = "v18.0.0"),
            githubReleaseResponse(tagName = "v17.0.0"),
        )
        coEvery { apiCaller.callAllReleases(stack2.owner, stack2.repo) } returns listOf(
            githubReleaseResponse(tagName = "v14.0.0"),
        )

        val result = collector.collectAllReleases(listOf(stack1, stack2))

        assertThat(result).hasSize(3)
        assertThat(result.filter { it.techStack == stack1.name }).hasSize(2)
        assertThat(result.filter { it.techStack == stack2.name }).hasSize(1)
    }

    private fun githubReleaseResponse(
        tagName: String = "v1.0.0",
        publishedAt: String = "2026-04-18T00:00:00Z",
    ) = GithubReleaseResponse(
        id = 1L,
        tagName = tagName,
        name = null,
        body = null,
        publishedAt = publishedAt,
        prerelease = false,
        draft = false,
    )
}
