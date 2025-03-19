create table course
(
    course_id   varchar(64)                       not null
        primary key,
    course_name varchar(64)                       not null,
    description text                              not null,
    state       varchar(12) default 'not-started' not null,
    create_at   datetime                          not null,
    update_at   datetime                          not null,
    file_at     datetime                          null,
    check (`state` in (_utf8mb4\'not-started\',_utf8mb4\'active\',_utf8mb4\'filed\'))
);