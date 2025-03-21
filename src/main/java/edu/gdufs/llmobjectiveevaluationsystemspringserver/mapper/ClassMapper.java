package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.ClassNotice;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.JoinClass;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassMapper {

    @Insert("insert into class(class_id, course_id, class_name,state, create_at, update_at) " +
            "values (#{classId}, #{courseId}, #{className}, #{state}, now(), now())")
    void addClass(String classId, String courseId, String className, String state);

    @Select("select * from class where class_id = #{classId}")
    Class classInfo(String classId);

    @Select("select teacher_id from class_notice where class_notice_id=#{classNoticeId}")
    String getTeacherByClassNoticeId(String classNoticeId);

    @Select("select * from class_notice where class_notice_id=#{classNoticeId}")
    ClassNotice getClassNoticeById(String classNoticeId);

    @Select("select * from class_notice where title like CONCAT('%',#{keyword},'%')")
    List<ClassNotice> searchClassNotice(String keyword);

    @Select("select * from class where course_id=#{course_id}")
    List<Class> classList(String courseId);

    @Select("select student_id from join_class where class_id=#{classId}")
    List<String> students(String classId);

    @Select("select course_id from class where class_id=#{classId}")
    List<String> courses(String classId);

    @Select("select * from join_class where class_id = #{classId} and student_id = #{studentId}")
    JoinClass checkJoin(String classId, String studentId);

    @Insert("insert into join_class values (#{classId},#{studentId})")
    void joinClass(String classId, String studentId);

    @Delete("delete from class where class_id=#{classId}")
    void removeClass(String classId);

    @Delete("delete from class_notice where class_notice_id=#{classNoticeId};")
    void deleteNotice(String classNoticeId);

    @Update("update class set class_name=#{className} where class_id=#{classId}")
    void modifyClass(String classId, String className);

    @Update("update class set state=#{state}, update_at=now() where class_id=#{classId}")
    void updateState(String classId, String state);

    @Update("update class set state=#{state}, update_at=now() where course_id=#{classId}")
    void updateStateForCourse(String courseId, String state);

    @Update("update class set state='filed', file_at=now() where course_id=#{classId}")
    void fileForCourse(String courseId);

    @Update("update class_notice set title=#{title},content=#{content},update_at=now() where class_notice_id=#{classNoticeId}")
    void updateNotice(String classNoticeId, String title, String content);
}
