create table question
(
    question_id   varchar(64) not null
        primary key,
    teacher_id    varchar(64) not null,
    content       json        not null,
    answer        json        not null,
    question_type varchar(16) not null,
    create_at     datetime    not null,
    update_at     datetime    not null,
    constraint question_ibfk_1
        foreign key (teacher_id) references teacher (teacher_id),
    check (`question_type` in (_utf8mb4\'choice\',_utf8mb4\'fill-blank\',_utf8mb4\'programming\',_utf8mb4\'essay\'))
);

create index teacher_id
    on question (teacher_id);

