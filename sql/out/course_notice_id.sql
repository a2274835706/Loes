create table `course_notice` (
     `course_notice_id` varchar(64) comment '课程公告id' primary key ,
     `course_id` varchar(64) not null comment '课程id',
     `teacher_id` varchar(64) not null comment '教师id',
     `title` varchar(64) not null comment '标题',
     `content` text not null comment '内容',
     `create_at` datetime not null comment '创建时间',
     `update_at` datetime not null comment '更新时间',

     foreign key (`course_id`) references `course`(`course_id`) on delete cascade,
     foreign key (`teacher_id`) references `teacher`(`teacher_id`) on delete cascade
)
