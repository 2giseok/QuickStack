package com.project.admin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TechBlogApproval extends BaseEntity {

    @Id
    private Long id;

    @MapsId
    @JoinColumn(name = "tech_blog_id")
    @OneToOne
    private TechBlog techBlog;

    @Column(columnDefinition = "text")
    private String summary;

    public void update(String summary) {
        this.summary = summary;
    }

    public TechBlogApproval(TechBlog techBlog) {
        this.techBlog = techBlog;
    }
}
