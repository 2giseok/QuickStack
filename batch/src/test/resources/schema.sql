CREATE TABLE IF NOT EXISTS github_release
(
    tech_stack   VARCHAR(100) NOT NULL,
    tag_name     VARCHAR(100) NOT NULL,
    name         VARCHAR(500) NULL,
    body         TEXT         NULL,
    published_at DATETIME(6)  NOT NULL,
    prerelease   TINYINT(1)   NOT NULL DEFAULT 0,
    draft        TINYINT(1)   NOT NULL DEFAULT 0,
    status       VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (tech_stack, tag_name)
);

CREATE TABLE IF NOT EXISTS tech_blog
(
    id           BIGINT        NOT NULL,
    source       VARCHAR(100)  NOT NULL COMMENT '수집 소스 (BlogSource enum name)',
    region       VARCHAR(20)   NOT NULL COMMENT '지역 구분 (BlogRegion enum name)',
    title        VARCHAR(500)  NOT NULL COMMENT '포스트 제목',
    url          VARCHAR(1000) NOT NULL COMMENT '포스트 URL (중복 방지 unique key)',
    published_at DATETIME(6)   NOT NULL COMMENT '발행 시각',
    status       VARCHAR(20)   NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING | PUBLISHED | UNPUBLISHED | DELETED',
    created_at   DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '수집 시각',
    PRIMARY KEY (id),
    UNIQUE KEY uq_tech_blog_url (url(768))
    ) ENGINE = InnoDB
    DEFAULT CHARSET = utf8mb4
    COLLATE = utf8mb4_unicode_ci;