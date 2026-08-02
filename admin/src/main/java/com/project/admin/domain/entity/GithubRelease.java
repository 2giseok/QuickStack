package com.project.admin.domain.entity;

import com.project.admin.constant.Status;
import com.project.admin.constant.TechStack;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "github_release",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tech_stack","tag_name"})
})
@Entity
@Getter
@NoArgsConstructor
public class GithubRelease {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TechStack techStack;

    private String tagName;
    @Column(name = "name", length = 500)
    private String name;
    @Column(name = "body", columnDefinition = "mediumtext")
    private String body;
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    @Column(name = "prerelease")
    private Boolean prerelease;
    @Column(name = "draft")
    private Boolean draft;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;


    public String getReleaseUrl() {
        return techStack.releaseUrl(tagName);
    }
    public String getUrl() {
        return  techStack.url(tagName);
    }

    @Builder
    public GithubRelease(Long id,TechStack techStack,String tagName, String name, String body,Status status,LocalDateTime publishedAt) {
        this.id=id;
        this.techStack=techStack;
        this.tagName=tagName;
        this.name=name;
        this.body=body;
        this.status=status;
        this.publishedAt=publishedAt;
    }

}


