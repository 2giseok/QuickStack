package com.project.batch.repository

import com.project.batch.constants.Status
import com.project.batch.domain.GithubRelease
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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

        val sql = """
            INSERT INTO github_release (tech_stack, tag_name, name, body, published_at, prerelease, draft, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE tech_stack = tech_stack
        """.trimIndent()

        databaseClient.inConnection { connection ->
            val statement = connection.createStatement(sql)
            releases.forEachIndexed { index, release ->
                statement.bind(0, release.techStack)
                statement.bind(1, release.tagName)
                if (release.name != null) statement.bind(2, release.name) else statement.bindNull(2, String::class.java)
                if (release.body != null) statement.bind(3, release.body) else statement.bindNull(3, String::class.java)
                statement.bind(4, release.publishedAt)
                statement.bind(5, release.prerelease)
                statement.bind(6, release.draft)
                statement.bind(7, release.status.code)
                if (index < releases.size - 1) statement.add()
            }
            Flux.from(statement.execute())
                .flatMap { result -> Mono.from(result.rowsUpdated) }
                .then()
        }.awaitSingleOrNull()
    }

    suspend fun selectReleaseTodayPublished(date: LocalDate): List<GithubRelease> {
        val start = date.atStartOfDay(clock.zone).toInstant()
        val end = date.plusDays(1).atStartOfDay(clock.zone).toInstant()
        return databaseClient.sql("""
            SELECT * FROM github_release
            WHERE published_at >= :start AND published_at < :end
        """.trimIndent())
            .bind("start", start)
            .bind("end", end)
            .map { row ->
                GithubRelease(
                    techStack = row.get("tech_stack", String::class.java)!!,
                    tagName = row.get("tag_name", String::class.java)!!,
                    name = row.get("name", String::class.java),
                    body = row.get("body", String::class.java),
                    publishedAt = row.get("published_at", Instant::class.java)!!,
                    prerelease = row.get("prerelease", Boolean::class.java)!!,
                    draft = row.get("draft", Boolean::class.java)!!,
                    status = Status.fromCode(row.get("status", String::class.java)!!),
                )
            }
            .all()
            .collectList()
            .awaitSingle()
    }
}