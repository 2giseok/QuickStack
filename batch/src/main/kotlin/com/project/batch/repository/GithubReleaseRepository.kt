package com.project.batch.repository

import com.project.batch.constants.Status
import com.project.batch.constants.TechStack
import com.project.batch.domain.GithubRelease
import com.project.batch.utils.toInstantRange
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import java.time.Clock
import java.time.Instant
import java.time.LocalDate

@Repository
class GithubReleaseRepository(
    private val databaseClient: DatabaseClient,
    private val clock: Clock,
) {
    suspend fun bulkInsert(releases: List<GithubRelease>) {
        if (releases.isEmpty()) return

        val placeholders = releases.joinToString(", ") { "(?, ?, ?, ?, ?, ?, ?, ?, ?)" }
        val sql = """
            INSERT INTO github_release (id, tech_stack, tag_name, name, body, published_at, prerelease, draft, status)
            VALUES $placeholders
            ON DUPLICATE KEY UPDATE tech_stack = tech_stack
        """.trimIndent()

        var spec = databaseClient.sql(sql)
        releases.forEachIndexed { i, release ->
            val base = i * 9
            spec = spec.bind(base, release.id)
            spec = spec.bind(base + 1, release.techStack.code)
            spec = spec.bind(base + 2, release.tagName)
            spec = if (release.name != null) spec.bind(base + 3, release.name) else spec.bindNull(base + 3, String::class.java)
            spec = if (release.body != null) spec.bind(base + 4, release.body) else spec.bindNull(base + 4, String::class.java)
            spec = spec
                .bind(base + 5, release.publishedAt)
                .bind(base + 6, release.prerelease)
                .bind(base + 7, release.draft)
                .bind(base + 8, release.status.code)
        }

        spec.fetch().rowsUpdated().awaitSingle()
    }

    suspend fun selectReleaseTodayPublished(date: LocalDate): List<GithubRelease> {
        val (start, end) = date.toInstantRange(clock.zone)
        return databaseClient.sql("""
            SELECT * FROM github_release
            WHERE published_at >= :start AND published_at < :end
        """.trimIndent())
            .bind("start", start)
            .bind("end", end)
            .map { row ->
                GithubRelease(
                    id = row["id", Long::class.java]!!,
                    techStack = TechStack.fromCode(row["tech_stack", String::class.java]!!),
                    tagName = row["tag_name", String::class.java]!!,
                    name = row["name", String::class.java],
                    body = row["body", String::class.java],
                    publishedAt = row["published_at", Instant::class.java]!!,
                    prerelease = row["prerelease", Boolean::class.java]!!,
                    draft = row["draft", Boolean::class.java]!!,
                    status = Status.fromCode(row["status", String::class.java]!!),
                )
            }
            .all()
            .collectList()
            .awaitSingle()
    }
}