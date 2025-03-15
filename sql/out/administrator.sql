create table administrator
(
    administrator_id varchar(64)  not null
        primary key,
    user_id          varchar(64)  not null,
    access_token     varchar(256) not null,
    constraint administrator_ibfk_1
        foreign key (user_id) references user (user_id)
            on delete cascade
);

create index user_id
    on administrator (user_id);
