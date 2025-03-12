create database `loes` default charset 'utf8';
use `loes`;

# 数据库中所有主键id，均使用雪花算法生成

create table `user` (
    `user_id` bigint unsigned not null,
    `username` varchar(64) not null unique,
    `password` varchar(256) not null,  # SHA-256 加密后的密码
    `nickname` varchar(64) not null,
    `create_at` datetime not null,
    `update_at` datetime not null,
    primary key (`user_id`)
);

create table `student` (
    `student_id` bigint unsigned not null,
    `user_id` bigint unsigned not null,
    primary key (`student_id`),
    foreign key (`user_id`) references `user`(`user_id`) on delete cascade
);

create table `teacher` (
    `teacher_id` bigint unsigned not null,
    `user_id` bigint unsigned not null,
    primary key (`teacher_id`),
    foreign key (`user_id`) references `user`(`user_id`) on delete cascade
);

create table `administrator` (
    `administrator_id` bigint unsigned not null,
    `user_id` bigint unsigned not null,
    `access_token` varchar(256) not null,
    primary key (`administrator_id`),
    foreign key (`user_id`) references `user`(`user_id`) on delete cascade
);

create table `course` (
    `course_id` bigint unsigned not null,
    `course_name` varchar(64) not null,
    `description` text not null,
    `state` varchar(12) not null default 'not-started',
    `create_at` datetime not null,
    `update_at` datetime not null,
    `file_at` datetime,
    primary key (`course_id`),
    check (`state` in ('not-started', 'active', 'filed'))
);

create table `study` (
    `course_id` bigint unsigned not null,
    `student_id` bigint unsigned not null,
    primary key (`course_id`),
    foreign key (`student_id`) references `student`(`student_id`) on delete cascade
);

create table `teach` (
     `course_id` bigint unsigned not null,
     teacher_id bigint unsigned not null,
     primary key (`course_id`),
     foreign key (teacher_id) references `teacher`(`teacher_id`) on delete cascade
);

create table `class` (
    `class_id` bigint unsigned not null,
    `course_id` bigint unsigned not null,
    `class_name` varchar(64) not null,
    `state` varchar(12) not null default 'not-started',
    `create_at` datetime not null,
    `file_at` datetime not null,
    primary key (`class_id`),
    foreign key (`course_id`) references `course`(`course_id`) on delete cascade,
    check (`state` in ('not-started', 'active', 'filed'))
);

create table `join` (
    `class_id` bigint unsigned not null,
    `student_id` bigint unsigned not null,
    `join_at` datetime not null,
    `exit_at` datetime,
    `file_at` datetime,
    primary key (`class_id`, `student_id`),
    foreign key (`class_id`) references class(`class_id`) on delete cascade,
    foreign key (student_id) references student(`student_id`) on delete cascade
);

create table `assignment` (
    `assignment_id` bigint unsigned not null,
    `class_id` bigint unsigned not null,
    `teacher_id` bigint unsigned not null,
    `title` varchar(64) not null,
    `description` text not null,
    `deadline` datetime not null,
    `create_at` datetime not null,
    `update_at` datetime not null,
    primary key (`assignment_id`),
    foreign key (`class_id`) references `class`(`class_id`) on delete cascade,
    foreign key (`teacher_id`) references `teacher`(`teacher_id`)
);

create table `question` (
    `question_id` bigint unsigned not null,
    `assignment_id` bigint unsigned not null,
    `content` json not null,
    `score` int not null default 0,
    `sort_order` int not null default 0,
    `question_type` varchar(16) not null,
    `create_at` datetime not null,
    `update_at` datetime not null,
    primary key (`question_id`),
    foreign key (`assignment_id`) references `assignment`(`assignment_id`) on delete cascade,
    check(`question_type` in ('choice', 'fill-blank', 'programming', 'essay'))
);

create table `submission` (
    `submission_id` bigint unsigned not null,
    `assignment_id` bigint unsigned not null,
    `student_id` bigint unsigned not null,
    `total_score` int not null default 0,
    `submit_at` datetime not null,
    primary key (`submission_id`),
    foreign key (`assignment_id`) references `assignment`(`assignment_id`) on delete cascade
);

create table `submission_detail` (
    `detail_id` bigint unsigned not null,
    `submission_id` bigint unsigned not null,
    `process` json not null,
    `answer` json not null,
    `score` int not null default 0,
    `feedback` text not null,
    primary key (`detail_id`),
    foreign key (`submission_id`) references `submission`(`submission_id`) on delete cascade
);

INSERT INTO loes.user (user_id, username, password, nickname, create_at, update_at) VALUES (1899740163576168448, 'admin01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'admin01', '2025-03-12 16:35:22', '2025-03-12 16:35:25');
INSERT INTO loes.user (user_id, username, password, nickname, create_at, update_at) VALUES (1899779840676986880, 'user01', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'user01', '2025-03-12 19:09:44', '2025-03-12 19:09:44');

INSERT INTO loes.student (student_id, user_id) VALUES (1899780188665806848, 1899779840676986880);

INSERT INTO loes.teacher (teacher_id, user_id) VALUES (1899780188703555584, 1899779840676986880);

INSERT INTO loes.administrator (administrator_id, user_id, access_token) VALUES (1899740163576168449, 1899740163576168448, 'e21e85f646aa76f5730f607a1e529ac681552bcf52819e1d83ca2c307ea2d41f');
INSERT INTO loes.administrator (administrator_id, user_id, access_token) VALUES (1899780386699870208, 1899779840676986880, '01c19610c8665a73f1805e5b234301e72b1be054f20074b97064a9826bc89154');


