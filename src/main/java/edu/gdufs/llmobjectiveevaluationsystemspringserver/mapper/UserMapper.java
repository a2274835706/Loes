package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Administrator;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Student;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from user where user_id=#{userId}")
    User getUserByUserId(String userId);

    @Select("select * from user where username=#{username}")
    User getUserByUsername(String username);

    @Select("select * from student where user_id=#{userId}")
    Student getStudentByUserId(String userId);

    @Select("select * from student where student_id=#{studentId}")
    Student getStudentByStudentId(String studentId);

    @Select("select * from teacher where user_id=#{userId}")
    Teacher getTeacherByUserId(String userId);

    @Select("select * from teacher where teacher_id=#{teacherId}")
    Teacher getTeacherByTeacherId(String teacherId);

    @Select("select * from teacher, user where teacher_id=#{teacherId} and teacher.user_id=user.user_id")
    User getUserByTeacherId(String teacherId);

    @Select("select * from student, user where student_id=#{studentId} and student.user_id=user.user_id")
    User getUserByStudentId(String studentId);

    @Select("select * from administrator where user_id=#{userId}")
    Administrator getAdministratorByUserId(String userId);

    @Select("select * from administrator where administrator_id=#{administratorId}")
    Administrator getAdministratorByAdministratorId(String administratorId);

    @Insert("insert into user values (#{userId}, #{username}, #{password}, #{nickname}, now(), now())")
    void addUser(String userId, String username, String password, String nickname);

    @Select("select * from administrator where access_token=#{accessToken}")
    Administrator checkAccessToken(String accessToken);

    @Insert("insert into student values (#{studentId}, #{userId})")
    void addStudent(String studentId, String userId);

    @Insert("insert into teacher values (#{teacherId}, #{userId})")
    void addTeacher(String teacherId, String userId);

    @Insert("insert into administrator values (#{administratorId}, #{userId}, #{accessToken})")
    void addAdministrator(String administratorId, String userId, String accessToken);

    @Insert("insert into class_notice values (#{classNoticeId},#{classId},#{teacherId},#{title},#{content},now(),now())")
    void addClassNotice(String classNoticeId, String classId, String teacherId, String title, String content);

    @Delete("delete from user where user_id=#{userId}")
    void removeUser(String userId);

    @Update("update user set nickname=#{nickname}, update_at=now() where user_id=#{userId}")
    void modifyNickName(String userId, String nickname);

    @Select("select * from user where nickname like #{keyword}")
    List<User> searchUser(String keyword);

}
