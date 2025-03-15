create table course
(
    course_id   varchar(64)                       not null
        primary key,
    course_name varchar(64)                       not null,
    description text                              not null,
    state       varchar(12) default 'not-started' not null,
    create_at   datetime                          not null,
    update_at   datetime                          not null,
    file_at     datetime                          null,
    check (`state` in (_utf8mb4\'not-started\',_utf8mb4\'active\',_utf8mb4\'filed\'))
);

INSERT INTO loes_new.course (course_id, course_name, description, state, create_at, update_at, file_at) VALUES ('cour_1900784076667748352', '测试课程捏', '这是测试捏', 'not-started', '2025-03-15 13:40:13', '2025-03-15 13:40:13', null);
INSERT INTO loes_new.course (course_id, course_name, description, state, create_at, update_at, file_at) VALUES ('cour_1900785353988505600', '测试课程2222', '这是测试捏2222', 'active', '2025-03-15 13:45:17', '2025-03-15 13:59:01', null);
INSERT INTO loes_new.course (course_id, course_name, description, state, create_at, update_at, file_at) VALUES ('cour_1900810355127353344', '雀魂麻将实践课', '打爽了', 'active', '2025-03-15 15:24:38', '2025-03-15 15:29:38', null);
INSERT INTO loes_new.course (course_id, course_name, description, state, create_at, update_at, file_at) VALUES ('cour_1900810872981291008', '以撒的结合：重生', 'D6!', 'not-started', '2025-03-15 15:26:42', '2025-03-15 15:26:42', null);
INSERT INTO loes_new.course (course_id, course_name, description, state, create_at, update_at, file_at) VALUES ('cour_1900812073240100864', '斐济北大战甲基:八', 'D6!', 'active', '2025-03-15 15:31:28', '2025-03-15 15:33:10', null);
