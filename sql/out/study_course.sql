create table study_course
(
    course_id  varchar(64) not null,
    student_id varchar(64) not null,
    primary key (course_id, student_id),
    constraint study_course_ibfk_1
        foreign key (course_id) references course (course_id)
            on delete cascade,
    constraint study_course_ibfk_2
        foreign key (student_id) references student (student_id)
            on delete cascade
);

create index student_id
    on study_course (student_id);
