package com.project.crawler.entity;

import com.project.crawler.constants.Source;
import com.project.crawler.constants.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "tech_blog")
@NoArgsConstructor
public class TechBlog {

    @Id
    private Long id;

    private String url;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Source source;

    private LocalDateTime publishedAt;

    public void markAsUnpublished() {
        this.status=Status.UNPUBLISHED;
    }
    public void markAsFailed() {
        this.status=Status.FAILED;
    }

    @Builder
    public TechBlog(Long id, String url, Status status,LocalDateTime publishedAt) {
        this.id=id;
        this.url=url;
        this.status=status;
        this.publishedAt=publishedAt;
    }
}