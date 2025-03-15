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

INSERT INTO loes_new.user (user_id, username, password, nickname, create_at, update_at) VALUES ('user_1899813505259798528', 'user00', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'user00', '2025-03-15 13:11:49', '2025-03-15 13:11:50');
INSERT INTO loes_new.user (user_id, username, password, nickname, create_at, update_at) VALUES ('user_1900781940923957248', 'baba', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'baba', '2025-03-15 13:31:44', '2025-03-15 13:31:44');
INSERT INTO loes_new.user (user_id, username, password, nickname, create_at, update_at) VALUES ('user_1900785736966209536', 'gyl', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'gyl', '2025-03-15 13:46:49', '2025-03-15 13:46:49');
