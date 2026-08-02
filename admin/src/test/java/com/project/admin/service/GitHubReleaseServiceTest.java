package com.project.admin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.admin.constant.Status;
import com.project.admin.constant.TechStack;
import com.project.admin.controller.dto.request.githubrelease.GitHubReleaseListRequest;
import com.project.admin.controller.dto.request.techblog.TechBlogListRequest;
import com.project.admin.domain.entity.GithubRelease;
import com.project.admin.domain.entity.GithubReleaseApproval;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import com.project.admin.domain.exception.release.ReleaseException;
import com.project.admin.domain.exception.techblog.TechBlogException;
import com.project.admin.repository.GithubReleaseApprovalRepository;
import com.project.admin.repository.GithubReleaseRepository;
import com.project.admin.service.dto.githubrelease.GitHubReleaseDetailResult;
import com.project.admin.service.dto.githubrelease.GitHubReleaseResult;
import com.project.admin.service.dto.techblog.TechBlogDetailResult;
import com.project.admin.service.dto.techblog.TechBlogResult;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class GitHubReleaseServiceTest {

    @InjectMocks
    private GitHubReleaseService gitHubReleaseService;
    @Mock
    private  GithubReleaseRepository githubReleaseRepository;
    @Mock
    private  GithubReleaseApprovalRepository githubReleaseApprovalRepository;


    private GithubRelease createGithubRelease(Long id, TechStack techStack,String tagName) {
        return GithubRelease.builder()
                .id(id)
                .techStack(techStack)
                .tagName(tagName)
                .name(tagName)
                .body("릴리즈 본문")
                .status(Status.PENDING)
                .publishedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getGithubReleases() {
        GitHubReleaseListRequest request = new GitHubReleaseListRequest(Status.PENDING, null, null,1,20);

        GithubRelease release = createGithubRelease(1L,TechStack.SPRING_BOOT ,"v0.0.1");
        GithubRelease release2 = createGithubRelease(1L,TechStack.SPRING_BOOT, "v0.0.2");

        PageImpl<GithubRelease> page = new PageImpl<>(List.of(release, release2));

        when(githubReleaseRepository.findByStatusAndTechStacks(eq(Status.PENDING),eq(null),any(Pageable.class),eq(null)))
                .thenReturn(page);
        Page<GitHubReleaseResult> results = gitHubReleaseService.getGitHubReleases(request);

        assertThat(results.getSize()).isEqualTo(2);
        assertThat(results.getContent().get(0).techStack()).isEqualTo(TechStack.SPRING_BOOT.name());
        assertThat(results.getContent().get(1).name()).isEqualTo("v0.0.2");

    }

    @Test
    void getGithubRelease() {
        GithubRelease release = createGithubRelease(1L,TechStack.SPRING_BOOT ,"v0.0.1");

        when(githubReleaseRepository.findById(1L)).thenReturn(Optional.ofNullable(release));
        when(githubReleaseApprovalRepository.findByGithubRelease(release)).thenReturn(Optional.empty());

        GitHubReleaseDetailResult result = gitHubReleaseService.getGitHubRelease(1L);
        assertThat(result.body()).isEqualTo("릴리즈 본문");
        assertThat(result.status()).isEqualTo(Status.PENDING);
    }
    @Test
    void 존재하지_않는_techBlog_조회() {

        when(githubReleaseRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> gitHubReleaseService.getGitHubRelease(1L))
                .isInstanceOf(ReleaseException.class);

    }

    @Test
    void updateStatus() {
        GithubRelease release = createGithubRelease(1L,TechStack.SPRING_BOOT ,"v0.0.1");
        GithubReleaseApproval approval = new GithubReleaseApproval(release);
        List<Long> ids = List.of(1L);

        when(githubReleaseRepository.findAllById(ids)).thenReturn(List.of(release));
        when(githubReleaseApprovalRepository.findAllById(ids)).thenReturn(List.of(approval));

        gitHubReleaseService.updateStatus(ids,Status.UNPUBLISHED);

        verify(githubReleaseRepository).updateStatusByIds(ids,Status.UNPUBLISHED);
        ArgumentCaptor<List<GithubReleaseApproval>> captor = ArgumentCaptor.forClass(List.class);
        verify(githubReleaseApprovalRepository).saveAll(captor.capture());

        List<GithubReleaseApproval> value = captor.getValue();
        assertThat(value.get(0)).isEqualTo(approval);
        assertThat(value.get(0).getGithubRelease()).isEqualTo(release);
    }
    @Test
    void 기존_승인X() {

        GithubRelease release = createGithubRelease(1L,TechStack.SPRING_BOOT ,"v0.0.1");
        List<Long> ids = List.of(1L);

        when(githubReleaseRepository.findAllById(ids)).thenReturn(List.of(release));
        when(githubReleaseApprovalRepository.findAllById(ids)).thenReturn(List.of());

        gitHubReleaseService.updateStatus(ids,Status.UNPUBLISHED);

        ArgumentCaptor<List<GithubReleaseApproval>> captor = ArgumentCaptor.forClass(List.class);
        verify(githubReleaseApprovalRepository).saveAll(captor.capture());

        List<GithubReleaseApproval> value = captor.getValue();
        assertThat(value.get(0).getGithubRelease()).isEqualTo(release);
    }
}