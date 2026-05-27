package com.project.admin.repository.quertdsl;

import com.project.admin.constant.Source;
import com.project.admin.constant.Status;
import com.project.admin.domain.entity.QTechBlog;
import com.project.admin.domain.entity.TechBlog;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class TechBlogRepositoryImpl implements TechBlogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<TechBlog> findByStatusAndSource(Status status, Source source, Pageable pageable) {
        QTechBlog techBlog = QTechBlog.techBlog;

        BooleanExpression condition = techBlog.status.eq(status);

        if (source != null) {
            condition = condition.and(techBlog.source.eq(source));
        }
        List<TechBlog> content = queryFactory
                .selectFrom(techBlog)
                .where(condition)
                .orderBy(techBlog.publishedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(techBlog.count())
                .from(techBlog)
                .where(condition)
                .fetchOne();
        return new PageImpl<>(content,pageable, total !=null ? total: 0);
    }
}
