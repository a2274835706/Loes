create table join_class
(
    class_id   varchar(64) not null,
    student_id varchar(64) not null,
    primary key (class_id, student_id),
    constraint join_class_ibfk_1
        foreign key (class_id) references class (class_id)
            on delete cascade,
    constraint join_class_ibfk_2
        foreign key (student_id) references student (student_id)
            on delete cascade
);

create index student_id
    on join_class (student_id);

