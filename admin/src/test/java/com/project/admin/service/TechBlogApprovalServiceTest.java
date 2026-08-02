package com.project.admin.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.project.admin.constant.Status;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import com.project.admin.repository.TechBlogApprovalRepository;
import com.project.admin.repository.TechBlogRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TechBlogApprovalServiceTest {

    @Mock
    private  TechBlogApprovalRepository techBlogApprovalRepository;
    @Mock
    private TechBlogRepository techBlogRepository;

    @InjectMocks
    private TechBlogApprovalService techBlogApprovalService;

    private TechBlog createTechBlog(Long id, String url) {
        return TechBlog.builder()
                .id(id)
                .url(url)
                .status(Status.PENDING)
                .publishedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void 존재하지않은_요약() {
        String url = "https://example.com";
        TechBlog techBlog = createTechBlog(1L, url);

        when(techBlogRepository.findById(1L)).thenReturn(Optional.ofNullable(techBlog));
        when(techBlogApprovalRepository.findByTechBlogId(1L)).thenReturn(Optional.empty());

        techBlogApprovalService.save(1L,"요약 내용");
        ArgumentCaptor<TechBlogApproval>captor = ArgumentCaptor.forClass(TechBlogApproval.class);
        verify(techBlogApprovalRepository).save(captor.capture());

        TechBlogApproval value = captor.getValue();

        assertThat(value.getSummary()).isEqualTo("요약 내용");
        assertThat(value.getTechBlog()).isEqualTo(techBlog);

    }
    @Test
    void 요약_업데이트() {
        String url = "https://example.com";
        TechBlog techBlog = createTechBlog(1L, url);
        TechBlogApproval approval = new TechBlogApproval(techBlog);

        when(techBlogRepository.findById(1L)).thenReturn(Optional.ofNullable(techBlog));
        when(techBlogApprovalRepository.findByTechBlogId(1L)).thenReturn(Optional.of(approval));

        techBlogApprovalService.save(1L,"업데이트된 요약");
        verify(techBlogApprovalRepository).save(approval);

        assertThat(approval.getSummary()).isEqualTo("업데이트된 요약");
        assertThat(approval.getTechBlog()).isEqualTo(techBlog);

    }
    @Test
    void save() {
        when(techBlogRepository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> techBlogApprovalService.save(1L,"요약"))
                .isInstanceOf(NoSuchElementException.class);
    }
}