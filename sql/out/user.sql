create table user
(
    user_id   varchar(64)  not null
        primary key,
    username  varchar(64)  not null,
    password  varchar(256) not null,
    nickname  varchar(64)  not null,
    create_at datetime     not null,
    update_at datetime     not null,
    constraint username
        unique (username)
);
