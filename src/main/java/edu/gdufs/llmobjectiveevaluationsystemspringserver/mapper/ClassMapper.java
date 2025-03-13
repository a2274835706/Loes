package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Join;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Insert("insert into `class`(`class_id`, `course_id`, `class_name`,`state`, `create_at`, `update_at`) " +
            "values (#{classId}, #{courseId}, #{className}, #{state}, now(), now())")
    void addClass(long classId, long courseId, String className, String state);

    @Select("select * from `class` where `class_id` = #{classId}")
    Class classInfo(long classId);

    @Select("select * from `class` where `course_id`=#{course_id}")
    List<Class> classList(long courseId);

    @Select("select `student_id` from `join` where `class_id`=#{classId}")
    List<Long> students(long classId);

    @Select("select * from `join` where `class_id` = #{classId} and `student_id` = #{studentId}")
    Join checkJoin(long classId, long studentId);

    @Insert("insert into `join` values (#{classId},#{studentId})")
    void joinClass(long classId, long studentId);

    @Delete("delete from `class` where `class_id`=#{classId}")
    void removeClass(long classId);

    @Update("update `class` set `class_name`=#{className} where `class_id`=#{classId}")
    void modifyClass(long classId, String className);

    @Update("update `class` set `state`=#{state}, `update_at`=now() where `class_id`=#{classId}")
    void updateState(long classId, String state);

    @Update("update `class` set `state`=#{state}, `update_at`=now() where `course_id`=#{classId}")
    void updateStateForCourse(long courseId, String state);

    @Update("update `class` set `state`='filed', `file_at`=now() where `course_id`=#{classId}")
    void fileForCourse(long courseId);

}
