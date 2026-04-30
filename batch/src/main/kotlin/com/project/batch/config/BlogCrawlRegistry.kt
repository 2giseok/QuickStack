package com.project.batch.config

import com.project.batch.constants.BlogSource

object BlogCrawlRegistry {

    private val configs = mapOf(
        BlogSource.NAVER_D2 to HtmlCrawlSpec(
            listSelector = ".post_list .post_item",
            titleSelector = ".title",
            linkSelector = "a",
            dateSelector = ".date",
            dateAttribute = "text",
        ),
        BlogSource.TOSS_TECH to HtmlCrawlSpec(
            listSelector = "article",
            titleSelector = "h2",
            linkSelector = "a",
            dateSelector = "time",
        ),
        BlogSource.KAKAO_BANK to HtmlCrawlSpec(
            listSelector = "article",
            titleSelector = "h2",
            linkSelector = "a",
            dateSelector = "time",
        ),
        BlogSource.OLIVE_YOUNG to HtmlCrawlSpec(
            listSelector = "article",
            titleSelector = "h2",
            linkSelector = "a",
            dateSelector = "time",
        ),
    )

    operator fun get(source: BlogSource): HtmlCrawlSpec? = configs[source]
}