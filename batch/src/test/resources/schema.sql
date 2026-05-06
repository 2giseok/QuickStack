create table if not exists github_release(
    id           bigint                        not null primary key,
    tech_stack   varchar(30)                   not null,
    tag_name     varchar(100)                  not null,
    name         varchar(500)                  null,
    body         mediumtext                    null,
    published_at datetime(6)                   not null,
    prerelease   tinyint(1)  default 0         not null,
    draft        tinyint(1)  default 0         not null,
    status       varchar(15) default 'PENDING' not null,
    constraint uk_github_release unique (tech_stack, tag_name)
);

create table if not exists tech_blog
(
    id           bigint                                   not null
        primary key,
    source       varchar(20)                              not null,
    region       varchar(15)                              not null,
    title        varchar(255)                             null,
    url          varchar(1000)                            not null,
    published_at datetime(6)                              not null,
    tags         json                                     null,
    status       varchar(15) default 'PENDING'            not null,
    created_at   datetime(6) default CURRENT_TIMESTAMP(6) not null,
    constraint uq_tech_blog_url
        unique (url(768))
);
