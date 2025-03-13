package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Study;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teach;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("insert into `course`(`course_id`, `course_name`, `description`, `create_at`, `update_at`)" +
            "values (#{courseId}, #{courseName}, #{description}, now(), now())")
    void addCourse(long courseId, String courseName, String description);

    @Update("update `course` set `state`=#{state}, `update_at`=now() where `course_id`=#{courseId}")
    void updateState(long courseId, String state);

    @Update("update `course` set `course_name`=#{courseName}, `description`=#{description}, `update_at`=now() " +
            "where `course_id`=#{courseId}")
    void updateInfo(long courseId, String courseName, String description);

    @Update("update `course` set `state`='filed', `file_at`=now() where `course_id`=#{courseId}")
    void file(long courseId);

    @Insert("insert into `teach` values (#{courseId}, #{teacherId})")
    void addTeach(long courseId, long teacherId);

    @Insert("insert into `study` values (#{courseId}, #{studentId})")
    void addStudy(long courseId, long studentId);

    @Select("select * from `teach` where `course_id`=#{courseId} and `teacher_id`=#{teacherId}")
    Teach checkTeach(long courseId, long teacherId);

    @Select("select * from `study` where `course_id`=#{courseId} and `student_id`=#{studentId}")
    Study checkStudy(long courseId, long studentId);

    @Select("select * from `course` where `course_id`=#{courseId}")
    Course courseInfo(long courseId);

    @Select("select `course_id` from `teach` where `teacher_id`=#{teacherId}")
    List<Long> getTeachList(long teacherId);

    @Select("select `course_id` from `study` where `student_id`=#{studentId}")
    List<Long> getStudyList(long studentId);

    @Select("select `teacher_id` from `teach` where `course_id`=#{courseId}")
    List<Long> getTeachers(long courseId);

    @Select("select * from `course` where `course_name` like #{keyword}")
    List<Course> search(String keyword);

    @Delete("delete from `course` where `course_id`=#{courseId}")
    void removeCourse(long courseId);

}
