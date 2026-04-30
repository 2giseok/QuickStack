package com.project.batch.config

data class HtmlCrawlSpec(
    val listSelector: String,
    val titleSelector: String,
    val linkSelector: String,
    val dateSelector: String,
    val dateAttribute: String = "datetime",
)