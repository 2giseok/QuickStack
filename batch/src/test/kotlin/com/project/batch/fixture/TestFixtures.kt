package com.project.batch.fixture

import com.project.batch.constants.Region
import com.project.batch.constants.Source
import com.project.batch.constants.TechStack
import com.project.batch.domain.GithubRelease
import com.project.batch.domain.TechBlog
import java.time.Instant

object TestFixtures {

    fun githubRelease(
        techStack: TechStack = TechStack.REACT,
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

    fun techBlog(
        source: String = Source.KAKAO.name,
        region: Region = Region.DOMESTIC,
        title: String = "테스트 블로그",
        url: String = "https://tech.kakao.com/2026/04/19/test",
        publishedAt: Instant = Instant.parse("2026-04-19T00:00:00Z"),
        tags: List<String> = emptyList(),
    ) = TechBlog(
        id = 1L,
        source = source,
        region = region,
        title = title,
        url = url,
        publishedAt = publishedAt,
        tags = tags,
    )
}
