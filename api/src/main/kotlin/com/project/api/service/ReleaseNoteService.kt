package com.project.api.service

import com.project.api.constants.Category
import com.project.api.constants.TechStack
import com.project.api.repository.GithubReleaseRepository
import com.project.api.service.dto.PageResult
import com.project.api.service.dto.ReleaseNoteResult
import com.project.api.service.dto.ReleaseNoteResults
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ReleaseNoteService(
    private val githubReleaseRepository: GithubReleaseRepository,
) {

    fun getReleaseNotes(category: Category, keyword: String?, page: Int, size: Int): ReleaseNoteResults {
        val pageable = PageRequest.of(page - 1, size)
        val techStacks = if (category == Category.ALL) null else category.techStacks

        // Early Return
        if (techStacks != null && techStacks.isEmpty()) {
            return ReleaseNoteResults.empty(pageable)
        }
        val trimmedKeyword = keyword?.takeIf { it.isNotBlank() }
        val matchedTechStacks = trimmedKeyword?.let { TechStack.search(it) } ?: emptySet()

        val result = githubReleaseRepository.findByTechStacksAndKeyword(
            techStacks ?: emptySet(),
            matchedTechStacks,
            trimmedKeyword,
            pageable
        )

        return ReleaseNoteResults.of(
            releaseNotes = result.content.map { ReleaseNoteResult.from(it) },
            pagination = PageResult.from(result),
        )
    }
}