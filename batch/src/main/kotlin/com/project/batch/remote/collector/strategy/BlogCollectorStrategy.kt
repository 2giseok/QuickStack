package com.project.batch.remote.collector.strategy

import com.project.batch.constants.BlogSource
import com.project.batch.constants.CollectionType
import com.project.batch.domain.TechBlog

interface BlogCollectorStrategy {

    fun supports(type: CollectionType): Boolean

    suspend fun collect(source: BlogSource): List<TechBlog>
}