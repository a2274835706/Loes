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
    primary key (`course_id`, `student_id`),
    foreign key (`course_id`) references `course`(`course_id`) on delete cascade,
    foreign key (`student_id`) references `student`(`student_id`) on delete cascade
);

create table `teach` (
     `course_id` bigint unsigned not null,
     `teacher_id` bigint unsigned not null,
     primary key (`course_id`, `teacher_id`),
    foreign key (`course_id`) references `course`(`course_id`) on delete cascade,
     foreign key (teacher_id) references `teacher`(`teacher_id`) on delete cascade
);

create table `class` (
    `class_id` bigint unsigned not null,
    `course_id` bigint unsigned not null,
    `class_name` varchar(64) not null,
    `state` varchar(12) not null default 'not-started',
    `create_at` datetime not null,
    `update_at` datetime not null,
    `file_at` datetime,
    primary key (`class_id`),
    foreign key (`course_id`) references `course`(`course_id`) on delete cascade,
    check (`state` in ('not-started', 'active', 'filed'))
);

create table `join` (
    `class_id` bigint unsigned not null,
    `student_id` bigint unsigned not null,
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


