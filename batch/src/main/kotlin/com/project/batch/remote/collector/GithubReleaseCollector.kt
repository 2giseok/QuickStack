package com.project.batch.remote.collector

import com.project.batch.constants.TechStack
import com.project.batch.domain.GithubRelease
import com.project.batch.remote.GithubReleaseApiCaller
import com.project.batch.utils.Snowflake
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class GithubReleaseCollector(
    private val githubReleaseApiCaller: GithubReleaseApiCaller,
    private val snowflake: Snowflake,
) {
    companion object {
        private val log = LoggerFactory.getLogger(GithubReleaseCollector::class.java)
    }

    suspend fun collectAll(techStacks: List<TechStack>): List<GithubRelease> =
        supervisorScope {
            techStacks.map { techStack ->
                async {
                    runCatching { githubReleaseApiCaller.callAllReleases(techStack.owner, techStack.repo) }
                        .onFailure { log.error("Failed to collect releases for ${techStack.owner}/${techStack.repo}", it) }
                        .getOrElse { emptyList() }
                        .map { release ->
                            GithubRelease(
                                id = snowflake.nextId(),
                                techStack = techStack,
                                tagName = release.tagName,
                                name = release.name,
                                body = release.body,
                                publishedAt = Instant.parse(release.publishedAt),
                                prerelease = release.prerelease,
                                draft = release.draft,
                            )
                        }
                }
            }.awaitAll().flatten()
        }
}