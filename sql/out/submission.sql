create table submission
(
    submission_id varchar(64)   not null
        primary key,
    release_id    varchar(64)   not null,
    student_id    varchar(64)   not null,
    question_id   varchar(64)   not null,
    process       json          not null,
    answer        json          not null,
    score         int default 0 not null,
    feedback      text          not null,
    submit_at     datetime      not null,
    update_at     datetime      not null,
    correct_at    datetime      null,
    constraint submission_ibfk_1
        foreign key (release_id) references release_assignment (release_id)
            on delete cascade,
    constraint submission_ibfk_2
        foreign key (student_id) references student (student_id)
            on delete cascade,
    constraint submission_ibfk_3
        foreign key (question_id) references question (question_id)
            on delete cascade
);

create index question_id
    on submission (question_id);

create index release_id
    on submission (release_id);

create index student_id
    on submission (student_id);

