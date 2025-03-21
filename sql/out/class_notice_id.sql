create table `class_notice` (
    `class_notice_id` varchar(64) comment '班级公告id' primary key ,
    `class_id` varchar(64) not null comment '班级id',
    `teacher_id` varchar(64) not null comment '教师id',
    `title` varchar(64) not null comment '标题',
    `content` text not null comment '内容',
    `create_at` datetime not null comment '创建时间',
    `update_at` datetime not null comment '更新时间',

    foreign key (`class_id`) references `class`(`class_id`) on delete cascade,
    foreign key (`teacher_id`) references `teacher`(`teacher_id`) on delete cascade
)
