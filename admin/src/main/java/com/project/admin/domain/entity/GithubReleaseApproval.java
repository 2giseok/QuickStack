package com.project.admin.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
public class GithubReleaseApproval extends  BaseEntity {

    @EmbeddedId
    private GithubRelease.GithubReleaseId id;

    @MapsId
    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "tech_stack"),
            @JoinColumn(name = "tag_name")
    })
    private GithubRelease githubRelease;

    public GithubReleaseApproval(GithubRelease release) {
        this.githubRelease = release;
    }
}
