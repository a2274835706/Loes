package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Administrator;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Student;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from `user` where `user_id`=#{userId}")
    User getUserByUserId(long userId);

    @Select("select * from `user` where `username`=#{username}")
    User getUserByUsername(String username);

    @Select("select * from `student` where `user_id`=#{userId}")
    Student getStudentByUserId(long userId);

    @Select("select * from `student` where `student_id`=#{studentId}")
    Student getStudentByStudentId(long studentId);

    @Select("select * from `teacher` where `user_id`=#{userId}")
    Teacher getTeacherByUserId(long userId);

    @Select("select * from `teacher` where `teacher_id`=#{teacherId}")
    Teacher getTeacherByTeacherId(long teacherId);

    @Select("select * from `administrator` where `user_id`=#{userId}")
    Administrator getAdministratorByUserId(long userId);

    @Select("select * from `administrator` where `administrator_id`=#{administratorId}")
    Administrator getAdministratorByAdministratorId(long administratorId);

    @Insert("insert into `user` values (#{userId}, #{username}, #{password}, #{nickname}, now(), now())")
    void addUser(long userId, String username, String password, String nickname);

    @Select("select * from `administrator` where `access_token`=#{accessToken}")
    Administrator checkAccessToken(String accessToken);

    @Insert("insert into `student` values (#{studentId}, #{userId})")
    void addStudent(long studentId, long userId);

    @Insert("insert into `teacher` values (#{teacherId}, #{userId})")
    void addTeacher(long teacherId, long userId);

    @Insert("insert into `administrator` values (#{administratorId}, #{userId}, #{accessToken})")
    void addAdministrator(long administratorId, long userId, String accessToken);

    @Delete("delete from `user` where `user_id`=#{userId}")
    void removeUser(long userId);

    @Update("update `user` set `nickname`=#{nickname}, `update_at`=now() where `user_id`=#{userId}")
    void modifyNickName(long userId, String nickname);

    @Select("select * from `user` where `nickname` like #{keyword}")
    List<User> searchUser(String keyword);

}
