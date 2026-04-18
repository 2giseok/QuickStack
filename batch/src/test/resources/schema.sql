CREATE TABLE IF NOT EXISTS github_release
(
    tech_stack   VARCHAR(100) NOT NULL,
    tag_name     VARCHAR(100) NOT NULL,
    name         VARCHAR(500) NULL,
    body         TEXT   NULL,
    published_at DATETIME(6)  NOT NULL,
    prerelease   TINYINT(1)   NOT NULL DEFAULT 0,
    draft        TINYINT(1)   NOT NULL DEFAULT 0,
    status       VARCHAR(20)  NOT NULL DEFAULT 'PENDING',
    PRIMARY KEY (tech_stack, tag_name)
);