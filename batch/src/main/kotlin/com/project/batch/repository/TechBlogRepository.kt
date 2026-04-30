package com.project.batch.repository

import com.project.batch.domain.TechBlog
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class TechBlogRepository(
    private val databaseClient: DatabaseClient,
) {

    suspend fun bulkInsert(blogs: List<TechBlog>) {
        if (blogs.isEmpty()) return

        val placeholders = blogs.joinToString(", ") { "(?, ?, ?, ?, ?, ?, ?)" }
        val sql = """
            INSERT INTO tech_blog (id, source, region, title, url, published_at, status)
            VALUES $placeholders
            ON DUPLICATE KEY UPDATE url = url
        """.trimIndent()

        databaseClient.inConnection { connection ->
            val statement = connection.createStatement(sql)
            blogs.forEachIndexed { index, blog ->
                val offset = index * 7
                statement.bind(offset, blog.id)
                statement.bind(offset + 1, blog.source)
                statement.bind(offset + 2, blog.region.name)
                statement.bind(offset + 3, blog.title)
                statement.bind(offset + 4, blog.url)
                statement.bind(offset + 5, blog.publishedAt)
                statement.bind(offset + 6, blog.status.code)
            }
            Mono.from(statement.execute())
                .flatMap { result -> Mono.from(result.rowsUpdated) }
                .then()
        }.awaitSingleOrNull()
    }
}