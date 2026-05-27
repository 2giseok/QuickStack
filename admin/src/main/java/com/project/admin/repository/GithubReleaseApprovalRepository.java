package com.project.admin.repository;

import com.project.admin.domain.entity.GithubRelease;
import com.project.admin.domain.entity.GithubReleaseApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GithubReleaseApprovalRepository extends JpaRepository<GithubReleaseApproval,Long> {


    Optional<GithubReleaseApproval> findByGithubRelease(GithubRelease githubRelease);

}
