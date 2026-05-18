package com.project.admin.domain.entity;

import com.project.admin.constant.CrawlStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
public class TechBlogContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_blog_id")
    private TechBlog techBlog;

    @Column(columnDefinition = "longtext")
    private String content;

    @Enumerated(EnumType.STRING)
    private CrawlStatus status;

    private String errorName;
    private LocalDateTime createdAt;


}
