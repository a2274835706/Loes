create database loes_new default charset 'utf8';
use loes_new;

# 数据库中所有主键id均为(前缀_雪花序列)

create table user (
    user_id varchar(64) not null,
    username varchar(64) not null unique,
    password varchar(256) not null,  # SHA-256 加密后的密码
    nickname varchar(64) not null,
    create_at datetime not null,
    update_at datetime not null,
    primary key (user_id)
);

create table student (
    student_id varchar(64) not null,
    user_id varchar(64) not null,
    primary key (student_id),
    foreign key (user_id) references user(user_id) on delete cascade
);

create table teacher (
    teacher_id varchar(64) not null,
    user_id varchar(64) not null,
    primary key (teacher_id),
    foreign key (user_id) references user(user_id) on delete cascade
);

create table administrator (
    administrator_id varchar(64) not null,
    user_id varchar(64) not null,
    access_token varchar(256) not null,
    primary key (administrator_id),
    foreign key (user_id) references user(user_id) on delete cascade
);

create table course (
    course_id varchar(64) not null,
    course_name varchar(64) not null,
    description text not null,
    state varchar(12) not null default 'not-started',
    create_at datetime not null,
    update_at datetime not null,
    file_at datetime,
    primary key (course_id),
    check (state in ('not-started', 'active', 'filed'))
);

create table study_course (
    course_id varchar(64) not null,
    student_id varchar(64) not null,
    primary key (course_id, student_id),
    foreign key (course_id) references course(course_id) on delete cascade,
    foreign key (student_id) references student(student_id) on delete cascade
);

create table teach_course (
    course_id varchar(64) not null,
    teacher_id varchar(64) not null,
    primary key (course_id, teacher_id),
    foreign key (course_id) references course(course_id) on delete cascade,
    foreign key (teacher_id) references teacher(teacher_id) on delete cascade
);

create table class (
    class_id varchar(64) not null,
    course_id varchar(64) not null,
    class_name varchar(64) not null,
    state varchar(12) not null default 'not-started',
    create_at datetime not null,
    update_at datetime not null,
    file_at datetime,
    primary key (class_id),
    foreign key (course_id) references course(course_id) on delete cascade,
    check (state in ('not-started', 'active', 'filed'))
);

create table join_class (
    class_id varchar(64) not null,
    student_id varchar(64) not null,
    primary key (class_id, student_id),
    foreign key (class_id) references class(class_id) on delete cascade,
    foreign key (student_id) references student(student_id) on delete cascade
);

create table assignment (
    assignment_id varchar(64) not null,
    teacher_id varchar(64) not null,
    title varchar(64) not null,
    description text not null,
    create_at datetime not null,
    update_at datetime not null,
    primary key (assignment_id),
    foreign key (teacher_id) references teacher(teacher_id)
);

create table question (
    question_id varchar(64) not null,
    teacher_id varchar(64) not null,
    content json not null,
    answer json not null,
    question_type varchar(16) not null,
    create_at datetime not null,
    update_at datetime not null,
    primary key (question_id),
    foreign key (teacher_id) references teacher(teacher_id),
    check(question_type in ('choice', 'fill-blank', 'programming', 'essay'))
);

create table assignment_problem (
    assignment_problem_id varchar(64) not null,
    assignment_id varchar(64) not null,
    question_id varchar(64) not null,
    score int not null,
    primary key (assignment_problem_id),
    foreign key (assignment_id) references assignment(assignment_id) on delete cascade,
    foreign key (question_id) references question(question_id) on delete cascade,
    check(score >= 0)
);

create table release_assignment (
    release_id varchar(64) not null,
    class_id varchar(64) not null,
    assignment_id varchar(64) not null,
    release_name varchar(64) not null,
    description text,
    deadline datetime not null,
    release_at datetime not null,
    primary key (release_id),
    foreign key (class_id) references class(class_id) on delete cascade,
    foreign key (assignment_id) references assignment(assignment_id) on delete cascade
);

create table submission (
    submission_id varchar(64) not null,
    release_id varchar(64) not null,
    student_id varchar(64) not null,
    question_id varchar(64) not null,
    process json not null,
    answer json not null,
    score int not null default 0,
    feedback text not null,
    submit_at datetime not null,
    update_at datetime not null,
    correct_at datetime,
    primary key (submission_id),
    foreign key (release_id) references release_assignment(release_id) on delete cascade,
    foreign key (student_id) references student(student_id) on delete cascade,
    foreign key (question_id) references question(question_id) on delete cascade
);




