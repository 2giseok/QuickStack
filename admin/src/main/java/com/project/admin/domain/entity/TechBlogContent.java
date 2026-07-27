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
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechBlogContent {

    @Id
    private Long id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tech_blog_id")
    private TechBlog techBlog;

    @Column(columnDefinition = "longtext")
    private String content;

    @Enumerated(EnumType.STRING)
    private CrawlStatus status;

    private String errorName;
    private LocalDateTime createdAt;

    @Builder
    private TechBlogContent(Long id, TechBlog techBlog,String content,
            CrawlStatus status,String errorName, LocalDateTime createdAt) {
        this.id=id;
        this.techBlog=techBlog;
        this.content=content;
        this.status=status;
        this.errorName=errorName;
        this.createdAt=createdAt;
    }


}
