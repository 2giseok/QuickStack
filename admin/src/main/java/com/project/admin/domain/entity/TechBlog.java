package com.project.admin.domain.entity;

import com.project.admin.constant.BlogRegion;
import com.project.admin.constant.Source;
import com.project.admin.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;

@Entity
@Getter
public class TechBlog {

    @Id
    private Long id;
    @Column(name = "source")
    @Enumerated(EnumType.STRING)
    private Source source;
    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private BlogRegion blogRegion;
    @Column(name = "title")
    private String title;
    @Column(name = "url", length = 1000)
    private String url;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "tags")
    private String tags;

}
