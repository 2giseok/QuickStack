package com.project.admin.service;

import com.project.admin.constant.TechStack;
import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseUpdateStatusRequest.ReleaseId;
import com.project.admin.domain.entity.GithubRelease;
import com.project.admin.constant.Status;
import com.project.admin.domain.entity.GithubRelease.GithubReleaseId;
import com.project.admin.domain.entity.GithubReleaseApproval;
import com.project.admin.domain.exception.release.ReleaseErrorCode;
import com.project.admin.domain.exception.release.ReleaseException;
import com.project.admin.repository.GithubReleaseRepository;
import com.project.admin.repository.GithubReleaseApprovalRepository;
import com.project.admin.service.dto.githubrelease.GitHubReleaseResult;
import com.project.admin.service.dto.githubrelease.GitHubReleaseDetailResult;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GitHubReleaseService {

    private final GithubReleaseRepository githubReleaseRepository;
    private final GithubReleaseApprovalRepository githubReleaseApprovalRepository;

    public Page<GitHubReleaseResult> getGitHubReleases(Status status, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "publishedAt");
        return githubReleaseRepository.findByStatus(status, pageable)
                .map(GitHubReleaseResult::from);
    }

    public GitHubReleaseDetailResult getGitHubRelease(String techName, String tagName) {
        GithubReleaseId id = new GithubReleaseId(techName, tagName);

        GithubRelease release = githubReleaseRepository.findById(id)
                .orElseThrow(() -> new ReleaseException(ReleaseErrorCode.RELEASE_NOT_FOUND));

        TechStack techStack = TechStack.valueOf(release.getGithubReleaseId().getTechStack());

        String published = githubReleaseApprovalRepository.findByGithubRelease(release)
                .map(approval -> String.valueOf(approval.getUpdatedBy()))
                .orElse(null);

        return GitHubReleaseDetailResult.from(release, published, techStack);
    }

    @Transactional
    public void updateStatus(List<ReleaseId> ids, Status status) {
        List<GithubReleaseId> releaseIds = ids.stream()
                .map(id -> new GithubReleaseId(id.techStack(), id.tagName()))
                .toList();

        githubReleaseRepository.updateStatusByIds(releaseIds, status);

        List<GithubRelease> releases = githubReleaseRepository.findAllById(releaseIds);

        Map<GithubReleaseId, GithubReleaseApproval> approvalMap = githubReleaseApprovalRepository
                .findAllById(releaseIds)
                .stream()
                .collect(Collectors.toMap(
                        a -> a.getGithubRelease().getGithubReleaseId(),
                        a -> a
                ));

        List<GithubReleaseApproval> approvals = releases.stream()
                .map(release -> approvalMap.getOrDefault(
                        release.getGithubReleaseId(),
                        new GithubReleaseApproval(release)
                ))
                .toList();
        githubReleaseApprovalRepository.saveAll(approvals);
    }
}

