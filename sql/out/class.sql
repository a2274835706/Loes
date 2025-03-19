create table class
(
    class_id   varchar(64)                       not null
        primary key,
    course_id  varchar(64)                       not null,
    class_name varchar(64)                       not null,
    state      varchar(12) default 'not-started' not null,
    create_at  datetime                          not null,
    update_at  datetime                          not null,
    file_at    datetime                          null,
    constraint class_ibfk_1
        foreign key (course_id) references course (course_id)
            on delete cascade,
    check (`state` in (_utf8mb4\'not-started\',_utf8mb4\'active\',_utf8mb4\'filed\'))
);

create index course_id
    on class (course_id);

