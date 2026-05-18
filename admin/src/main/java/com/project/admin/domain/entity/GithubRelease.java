package com.project.admin.domain.entity;

import com.project.admin.constant.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Table(name = "github_release")
@Entity
@Getter
public class GithubRelease {

    @EmbeddedId
    private GithubReleaseId githubReleaseId;

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

    @Getter
    @Embeddable
    @EqualsAndHashCode
    @NoArgsConstructor
    public static class GithubReleaseId implements Serializable {

        @Column(name = "tech_stack", length = 100)
        private String techStack;
        @Column(name = "tag_name", length = 100)
        private String tagName;

        public GithubReleaseId(String techStack, String tagName) {
            this.techStack =techStack;
            this.tagName =tagName;
        }
    }
}


