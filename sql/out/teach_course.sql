create table teach_course
(
    course_id  varchar(64) not null,
    teacher_id varchar(64) not null,
    primary key (course_id, teacher_id),
    constraint teach_course_ibfk_1
        foreign key (course_id) references course (course_id)
            on delete cascade,
    constraint teach_course_ibfk_2
        foreign key (teacher_id) references teacher (teacher_id)
            on delete cascade
);

create index teacher_id
    on teach_course (teacher_id);

INSERT INTO loes_new.teach_course (course_id, teacher_id) VALUES ('cour_1900784076667748352', 'teac_1900783441050337280');
INSERT INTO loes_new.teach_course (course_id, teacher_id) VALUES ('cour_1900785353988505600', 'teac_1900783441050337280');
INSERT INTO loes_new.teach_course (course_id, teacher_id) VALUES ('cour_1900810355127353344', 'teac_1900783441050337280');
INSERT INTO loes_new.teach_course (course_id, teacher_id) VALUES ('cour_1900810872981291008', 'teac_1900783441050337280');
INSERT INTO loes_new.teach_course (course_id, teacher_id) VALUES ('cour_1900812073240100864', 'teac_1900783441050337280');
