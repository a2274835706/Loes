package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Insert("insert into `assignment` values (#{assignmentId}, #{courseId}, #{teacherId}, #{title}, #{description}, #{deadline}, now(), now())")
    void addAssignment(long assignmentId, long courseId, long teacherId, String title, String description, LocalDateTime deadline);

    @Select("select * from `assignment` where `assignment_id`=#{assignment}")
    Assignment assignmentInfo(long assignmentId);

    @Select("select * from `assignment` where `course_id`=#{courseId}")
    List<Assignment> assignmentOfCourse(long courseId);

    @Select("select * from `assignment` where `teacher_id`=#{teacherId}")
    List<Assignment> assignmentOfTeacher(long teacherId);

    @Update("update `assignment` set `title`=#{title}, `description`=#{description}, `deadline`=#{deadline}, `update_at`=now() where `assignment_id`=#{assignmentId}")
    void updateAssignment(long assignmentId, String title, String description, LocalDateTime deadline);

    @Delete("delete from `assignment` where `assignment_id`=#{assignmentID}")
    void removeAssignment(long assignmentId);

}
