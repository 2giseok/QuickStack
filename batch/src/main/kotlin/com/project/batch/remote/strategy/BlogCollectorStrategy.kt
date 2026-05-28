package com.project.batch.remote.strategy

import com.project.batch.constants.Source
import com.project.batch.constants.CollectionType
import com.project.batch.domain.TechBlog

interface BlogCollectorStrategy {

    fun supports(type: CollectionType): Boolean

    suspend fun collect(source: Source): List<TechBlog>
}