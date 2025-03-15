create table teacher
(
    teacher_id varchar(64) not null
        primary key,
    user_id    varchar(64) not null,
    constraint teacher_ibfk_1
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create index user_id
    on teacher (user_id);

INSERT INTO loes_new.teacher (teacher_id, user_id) VALUES ('teac_1900783441050337280', 'user_1900781940923957248');
INSERT INTO loes_new.teacher (teacher_id, user_id) VALUES ('teac_1900785906671943680', 'user_1900785736966209536');
