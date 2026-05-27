package com.project.admin.service;

import com.project.admin.constant.TechStack;
import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseListRequest;
import com.project.admin.domain.entity.GithubRelease;
import com.project.admin.constant.Status;
import com.project.admin.domain.entity.GithubReleaseApproval;
import com.project.admin.domain.exception.release.ReleaseErrorCode;
import com.project.admin.domain.exception.release.ReleaseException;
import com.project.admin.repository.GithubReleaseRepository;
import com.project.admin.repository.GithubReleaseApprovalRepository;
import com.project.admin.service.dto.githubrelease.GitHubReleaseResult;
import com.project.admin.service.dto.githubrelease.GitHubReleaseDetailResult;
import java.util.Arrays;
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

    public Page<GitHubReleaseResult> getGitHubReleases(GitHubReleaseListRequest request) {

        List<TechStack> techStacks = request.category() == null ? null :
                Arrays.stream(TechStack.values())
                        .filter(t -> t.getCategory() == request.category())
                        .toList();

        Pageable pageable = PageRequest.of(
                request.page() - 1,
                request.size(),
                Sort.Direction.DESC,
                "publishedAt");

        TechStack techStack = request.techStack() == null ? null :
                TechStack.valueOf(request.techStack());

        return githubReleaseRepository.findByStatusAndTechStacks(request.status(), techStacks,
                        pageable, techStack)
                .map(GitHubReleaseResult::from);
    }

    public GitHubReleaseDetailResult getGitHubRelease(Long id) {

        GithubRelease release = githubReleaseRepository.findById(id)
                .orElseThrow(() -> new ReleaseException(ReleaseErrorCode.RELEASE_NOT_FOUND));

        String published = githubReleaseApprovalRepository.findByGithubRelease(release)
                .map(approval -> String.valueOf(approval.getUpdatedBy()))
                .orElse(null);

        return GitHubReleaseDetailResult.from(release, published);
    }

    @Transactional
    public void updateStatus(List<Long> ids, Status status) {

        githubReleaseRepository.updateStatusByIds(ids, status);

        List<GithubRelease> releases = githubReleaseRepository.findAllById(ids);

        Map<Long, GithubReleaseApproval> approvalMap = githubReleaseApprovalRepository
                .findAllById(ids)
                .stream()
                .collect(Collectors.toMap(
                        a -> a.getGithubRelease().getId(),
                        a -> a
                ));

        List<GithubReleaseApproval> approvals = releases.stream()
                .map(release -> approvalMap.getOrDefault(
                        release.getId(),
                        new GithubReleaseApproval(release)
                ))
                .toList();
        githubReleaseApprovalRepository.saveAll(approvals);
    }
}

