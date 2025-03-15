create table assignment
(
    assignment_id varchar(64) not null
        primary key,
    teacher_id    varchar(64) not null,
    title         varchar(64) not null,
    description   text        not null,
    create_at     datetime    not null,
    update_at     datetime    not null,
    constraint assignment_ibfk_1
        foreign key (teacher_id) references teacher (teacher_id)
);

create index teacher_id
    on assignment (teacher_id);

