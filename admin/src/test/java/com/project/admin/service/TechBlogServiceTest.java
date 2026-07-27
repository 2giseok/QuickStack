package com.project.admin.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.project.admin.constant.Source;
import com.project.admin.constant.Status;
import com.project.admin.controller.dto.request.techblog.TechBlogListRequest;
import com.project.admin.domain.entity.TechBlog;
import com.project.admin.domain.entity.TechBlogApproval;
import com.project.admin.domain.exception.techblog.TechBlogException;
import com.project.admin.repository.TechBlogApprovalRepository;
import com.project.admin.repository.TechBlogRepository;
import com.project.admin.service.dto.techblog.TechBlogDetailResult;
import com.project.admin.service.dto.techblog.TechBlogResult;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
class TechBlogServiceTest {

    @Mock
    private  TechBlogRepository techBlogRepository;
    @Mock
    private  TechBlogApprovalRepository techBlogApprovalRepository;

    @InjectMocks
    private TechBlogService techBlogService;


    private TechBlog createTechBlog(Long id, String url) {
        return TechBlog.builder()
                .id(id)
                .url(url)
                .status(Status.PENDING)
                .publishedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getTechBlogs() {
        TechBlogListRequest request = new TechBlogListRequest(Status.PENDING, null, 1,20);

        TechBlog techBlog = createTechBlog(1L, "www.example.com/post/1");
        TechBlog techBlog2 = createTechBlog(1L, "www.example.com/post/2");

        PageImpl<TechBlog> page = new PageImpl<>(List.of(techBlog, techBlog2));

        when(techBlogRepository.findByStatusAndSource(eq(Status.PENDING),eq(null),any(Pageable.class)))
                .thenReturn(page);
        Page<TechBlogResult> blogs = techBlogService.getTechBlogs(request);

        assertThat(blogs.getSize()).isEqualTo(2);
        assertThat(blogs.getContent().get(0).url()).isEqualTo("www.example.com/post/1");
        assertThat(blogs.getContent().get(1).url()).isEqualTo("www.example.com/post/2");

    }

    @Test
    void getTechBlog() {
        TechBlog techBlog = createTechBlog(1L, "www.example.com/post/1");

        when(techBlogRepository.findById(1L)).thenReturn(Optional.ofNullable(techBlog));
        when(techBlogApprovalRepository.findByTechBlogId(1L)).thenReturn(Optional.empty());

        TechBlogDetailResult result = techBlogService.getTechBlog(1L);
        assertThat(result.url()).isEqualTo("www.example.com/post/1");
        assertThat(result.status()).isEqualTo(Status.PENDING);
    }
    @Test
    void 존재하지_않는_techBlog_조회() {


        when(techBlogRepository.findById(1L)).thenReturn(Optional.empty());
       assertThatThrownBy(() -> techBlogService.getTechBlog(1L))
               .isInstanceOf(TechBlogException.class);

    }

    @Test
    void 승인정보가_존재하지_않는_techBlog_조회() {
        TechBlog techBlog = createTechBlog(1L, "www.example.com/post/1");
        TechBlogApproval approval = new TechBlogApproval(techBlog);
        approval.update("요약된 내용");

        when(techBlogRepository.findById(1L)).thenReturn(Optional.ofNullable(techBlog));
        when(techBlogApprovalRepository.findByTechBlogId(1L)).thenReturn(Optional.of(approval));

        TechBlogDetailResult result = techBlogService.getTechBlog(1L);
        assertThat(result.summaries()).isEqualTo("요약된 내용");


    }

    @Test
    void updateStatus() {
        TechBlog techBlog = createTechBlog(1L, "www.example.com/post/1");
        TechBlogApproval approval = new TechBlogApproval(techBlog);
        List<Long> ids = List.of(1L);

        when(techBlogRepository.findAllById(ids)).thenReturn(List.of(techBlog));
        when(techBlogApprovalRepository.findAllByTechBlogIdIn(ids)).thenReturn(List.of(approval));

        techBlogService.updateStatus(ids,Status.UNPUBLISHED);

        verify(techBlogRepository).updateStatusByIds(ids,Status.UNPUBLISHED);
        ArgumentCaptor<List<TechBlogApproval>> captor = ArgumentCaptor.forClass(List.class);
        verify(techBlogApprovalRepository).saveAll(captor.capture());

        List<TechBlogApproval> value = captor.getValue();
        assertThat(value.get(0)).isEqualTo(approval);
        assertThat(value.get(0).getTechBlog()).isEqualTo(techBlog);
    }
    @Test
    void 기존_승인X() {

        TechBlog techBlog = createTechBlog(1L, "www.example.com/post/1");
        List<Long> ids = List.of(1L);

        when(techBlogRepository.findAllById(ids)).thenReturn(List.of(techBlog));
        when(techBlogApprovalRepository.findAllByTechBlogIdIn(ids)).thenReturn(List.of());

        techBlogService.updateStatus(ids,Status.UNPUBLISHED);

        ArgumentCaptor<List<TechBlogApproval>> captor = ArgumentCaptor.forClass(List.class);
        verify(techBlogApprovalRepository).saveAll(captor.capture());

        List<TechBlogApproval> value = captor.getValue();
        assertThat(value.get(0).getTechBlog()).isEqualTo(techBlog);
    }
}