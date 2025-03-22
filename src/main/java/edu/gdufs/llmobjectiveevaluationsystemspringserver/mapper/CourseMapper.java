package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.CourseNotice;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.StudyCourse;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.TeachCourse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Insert("insert into course(course_id, course_name, description, create_at, update_at)" +
            "values (#{courseId}, #{courseName}, #{description}, now(), now())")
    void addCourse(String courseId, String courseName, String description);

    @Update("update course set state=#{state}, update_at=now() where course_id=#{courseId}")
    void updateState(String courseId, String state);

    @Update("update course set course_name=#{courseName}, description=#{description}, update_at=now() " +
            "where course_id=#{courseId}")
    void updateInfo(String courseId, String courseName, String description);

    @Update("update course set state='filed', file_at=now() where course_id=#{courseId}")
    void file(String courseId);

    @Insert("insert into teach_course values (#{courseId}, #{teacherId})")
    void addTeach(String courseId, String teacherId);

    @Insert("insert into study_course values (#{courseId}, #{studentId})")
    void addStudy(String courseId, String studentId);

    @Select("select * from teach_course where course_id=#{courseId} and teacher_id=#{teacherId}")
    TeachCourse checkTeach(String courseId, String teacherId);

    @Select("select * from study_course where course_id=#{courseId} and student_id=#{studentId}")
    StudyCourse checkStudy(String courseId, String studentId);

    @Select("select * from course where course_id=#{courseId}")
    Course courseInfo(String courseId);

    @Select("select course_id from teach_course where teacher_id=#{teacherId}")
    List<String> getTeachList(String teacherId);

    @Select("select course_id from study_course where student_id=#{studentId}")
    List<String> getStudyList(String studentId);

    @Select("select teacher_id from teach_course where course_id=#{courseId}")
    List<String> getTeachers(String courseId);

    @Select("select * from course where course_name like #{keyword}")
    List<Course> search(String keyword);

    @Delete("delete from course where course_id=#{courseId}")
    void removeCourse(String courseId);

    @Select("select * from course_notice where course_notice_id=#{courseNoticeId}")
    CourseNotice getCourseNoticeById(String courseNoticeId);

    @Insert("insert into course_notice values (#{courseNoticeId},#{courseId},#{teacherId},#{title},#{content},now(),now())")
    void addCourseNotice(String courseNoticeId, String courseId, String teacherId, String title, String content);

    @Update("update course_notice set course_id=#{courseId},title=#{title},content=#{content},update_at=now() where course_notice_id=#{courseNoticeId}")
    void modifyCourseNotice(String courseNoticeId, String courseId, String title, String content);

    

    @Delete(("delete from course_notice where course_notice_id=#{courseNoticeId}"))
    boolean removeNotice(String courseNoticeId);

    @Select("select * from course_notice where title like CONCAT('%',#{keyword},'%')")
    List<CourseNotice> searchCourseNotice(String keyword);
}
