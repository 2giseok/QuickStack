package com.project.admin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class GithubReleaseApproval extends  BaseEntity {

   @Id
    private Long  id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "github_release_id")
    private GithubRelease githubRelease;

    public GithubReleaseApproval(GithubRelease release) {
        this.githubRelease = release;
    }
}
