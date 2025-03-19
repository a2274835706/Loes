create table student
(
    student_id varchar(64) not null
        primary key,
    user_id    varchar(64) not null,
    constraint student_ibfk_1
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create index user_id
    on student (user_id);
