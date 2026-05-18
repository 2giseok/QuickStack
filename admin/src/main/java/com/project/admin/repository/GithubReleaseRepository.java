package com.project.admin.repository;

import com.project.admin.domain.entity.GithubRelease;
import com.project.admin.constant.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GithubReleaseRepository extends JpaRepository<GithubRelease, GithubRelease.GithubReleaseId> {


    Page<GithubRelease> findByStatus(Status status, Pageable pageable);


    @Modifying
    @Query("UPDATE GithubRelease  g SET  g.status= :status where  g.githubReleaseId in :ids")
    void updateStatusByIds(@Param("ids")List<GithubRelease.GithubReleaseId> ids, @Param("status") Status status);
}
