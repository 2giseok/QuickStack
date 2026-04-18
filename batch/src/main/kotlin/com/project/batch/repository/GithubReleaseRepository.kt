package com.project.batch.repository

import com.project.batch.domain.GithubRelease
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.time.Instant
import java.time.LocalDate

@Repository
class GithubReleaseRepository(
    private val databaseClient: DatabaseClient,
) {
    companion object {
        private const val CHUNK_SIZE = 2000
    }

    suspend fun bulkInsert(releases: List<GithubRelease>) {
        releases.chunked(CHUNK_SIZE).forEach { chunk ->
            val placeholders = chunk.indices.joinToString(", ") { i ->
                "(:techStack$i, :tagName$i, :name$i, :body$i, :publishedAt$i, :prerelease$i, :draft$i, :status$i)"
            }
            var spec = databaseClient.sql(
                """
                INSERT INTO github_release (tech_stack, tag_name, name, body, published_at, prerelease, draft, status)
                VALUES $placeholders
                ON DUPLICATE KEY UPDATE tech_stack = tech_stack
                """.trimIndent()
            )
            chunk.forEachIndexed { i, release ->
                spec = spec.bind("techStack$i", release.techStack)
                spec = spec.bind("tagName$i", release.tagName)
                spec = if (release.name != null) spec.bind("name$i", release.name) else spec.bindNull("name$i", String::class.java)
                spec = if (release.body != null) spec.bind("body$i", release.body) else spec.bindNull("body$i", String::class.java)
                spec = spec.bind("publishedAt$i", release.publishedAt)
                spec = spec.bind("prerelease$i", release.prerelease)
                spec = spec.bind("draft$i", release.draft)
                spec = spec.bind("status$i", release.status.code)
            }
            spec.fetch().rowsUpdated().awaitSingle()
        }
    }

    suspend fun selectReleaseTodayPublished(date: LocalDate): List<GithubRelease> =
        databaseClient.sql("SELECT * FROM github_release WHERE published_at >= :startOfDay AND published_at < :endOfDay")
            .bind("startOfDay", date.atStartOfDay(com.project.batch.config.ClockConfig.zone).toInstant())
            .bind("endOfDay", date.plusDays(1).atStartOfDay(com.project.batch.config.ClockConfig.zone).toInstant())
            .map { row ->
                GithubRelease(
                    techStack = row.get("tech_stack", String::class.java)!!,
                    tagName = row.get("tag_name", String::class.java)!!,
                    name = row.get("name", String::class.java),
                    body = row.get("body", String::class.java),
                    publishedAt = row.get("published_at", Instant::class.java)!!,
                    prerelease = row.get("prerelease", Boolean::class.java)!!,
                    draft = row.get("draft", Boolean::class.java)!!,
                )
            }
            .all()
            .collectList()
            .awaitSingle()
}