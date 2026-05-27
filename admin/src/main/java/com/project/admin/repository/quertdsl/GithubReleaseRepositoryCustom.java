package com.project.admin.repository.quertdsl;

import com.project.admin.constant.Status;
import com.project.admin.constant.TechStack;
import com.project.admin.domain.entity.GithubRelease;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GithubReleaseRepositoryCustom {

    Page<GithubRelease> findByStatusAndTechStacks(
            Status status,
            List<TechStack> techStacks,
            Pageable pageable,
            TechStack techStack
    );

}
