create table release_assignment
(
    release_id    varchar(64) not null
        primary key,
    class_id      varchar(64) not null,
    assignment_id varchar(64) not null,
    release_name  varchar(64) not null,
    description   text        null,
    deadline      datetime    not null,
    release_at    datetime    not null,
    constraint release_assignment_ibfk_1
        foreign key (class_id) references class (class_id)
            on delete cascade,
    constraint release_assignment_ibfk_2
        foreign key (assignment_id) references assignment (assignment_id)
            on delete cascade
);

create index assignment_id
    on release_assignment (assignment_id);

create index class_id
    on release_assignment (class_id);

