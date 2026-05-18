package com.project.admin.domain.entity;

import com.project.admin.constant.BlogRegion;
import com.project.admin.constant.BlogSource;
import com.project.admin.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Getter
public class TechBlog {

    @Id
    private Long id;
    @Column(name = "source", length = 100)
    @Enumerated(EnumType.STRING)
    private BlogSource source;
    @Column(name = "region", length = 20)
    @Enumerated(EnumType.STRING)
    private BlogRegion region;
    @Column(name = "title", length = 500)
    private String title;
    @Column(name = "url", length = 1000)
    private String url;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

}
