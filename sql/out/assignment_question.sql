create table assignment_question
(
    assignment_question_id varchar(64) not null
        primary key,
    assignment_id          varchar(64) not null,
    question_id            varchar(64) not null,
    score                  int         not null,
    sort_order             int         not null,
    constraint assignment_question_ibfk_1
        foreign key (assignment_id) references assignment (assignment_id)
            on delete cascade,
    constraint assignment_question_ibfk_2
        foreign key (question_id) references question (question_id)
            on delete cascade,
    check (`score` >= 0)
);

create index assignment_id
    on assignment_question (assignment_id);

create index question_id
    on assignment_question (question_id);

