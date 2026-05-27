package com.project.admin.repository.quertdsl;

import com.project.admin.constant.Status;
import com.project.admin.constant.TechStack;
import com.project.admin.domain.entity.GithubRelease;
import com.project.admin.domain.entity.QGithubRelease;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class GithubReleaseRepositoryImpl implements GithubReleaseRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<GithubRelease> findByStatusAndTechStacks(Status status, List<TechStack> techStacks,
            Pageable pageable,TechStack techStack) {

        QGithubRelease githubRelease = QGithubRelease.githubRelease;

        BooleanExpression condition =githubRelease.status.eq(status);

        if (techStacks != null && !techStacks.isEmpty()) {

            condition = condition.and(
                    githubRelease.techStack.in(techStacks)
            );
        }
        if (techStack != null) {
            condition = condition.and(
                    githubRelease.techStack.eq(techStack)
            );
        }

        List<GithubRelease> content =  queryFactory
                .selectFrom(githubRelease)
                .where(condition)
                .orderBy(githubRelease.publishedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(githubRelease.count())
                .from(githubRelease)
                .where(condition)
                .fetchOne();

        return new PageImpl<>(content,pageable,total !=null ? total :0);
    }
}
