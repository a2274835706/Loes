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

INSERT INTO loes_new.student (student_id, user_id) VALUES ('stud_1900783440974839808', 'user_1900781940923957248');
INSERT INTO loes_new.student (student_id, user_id) VALUES ('stud_1900785906713886720', 'user_1900785736966209536');
