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

INSERT INTO loes_new.administrator (administrator_id, user_id, access_token) VALUES ('admi_1899813505259798529', 'user_1899813505259798528', '6f647391b1e9b34f06eb753ba4373057656274167da320b2f1481663eb16467c');
INSERT INTO loes_new.administrator (administrator_id, user_id, access_token) VALUES ('admi_1900783441012588544', 'user_1900781940923957248', '2c49c4f4fc575755d36b056e23436bc0966399f4e7a38cabd29eae70d3f63d36');
INSERT INTO loes_new.administrator (administrator_id, user_id, access_token) VALUES ('admi_1900785906625806336', 'user_1900785736966209536', '23d8a2c708ba5a65bfab505010faed59308e1920bf64d1ae309a2ecfc06d407b');
