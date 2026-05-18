package com.project.crawler.entity;

import com.project.crawler.constants.CrawlStatus;
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
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Getter
@Table(name = "tech_blog_content")
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

    // 최초 생성
    public static TechBlogContent init(TechBlog techBlog) {
        TechBlogContent entity = new TechBlogContent();
        entity.techBlog = techBlog;
        entity.createdAt = LocalDateTime.now();
        return entity;
    }

    // 성공시 덮어쓰기
    public void updateSuccess(String content) {
        this.content = content;
        this.status = CrawlStatus.SUCCESS;
        this.errorName = null;
    }

    // 실패시 덮어쓰기
    public void updateFailed(CrawlStatus status, String errorName) {
        this.status = status;
        this.errorName = errorName;  // 이전 에러 메시지 남김
    }
}