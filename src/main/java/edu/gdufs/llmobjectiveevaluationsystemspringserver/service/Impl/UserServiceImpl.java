package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response.UserInfo;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Administrator;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Student;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.User;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.UserService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.SHA256Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrefixSnowflake snowflake;

    @Override
    public User getUser(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    /**
     * 获取用户的身份信息
     * @param userId 用户Id
     * @return 用户信息
     */
    @Override
    public UserInfo getUserInfo(String userId) {
        UserInfo info = new UserInfo();
        info.setIdentity(new ArrayList<>());
        Student student = userMapper.getStudentByUserId(userId);
        if (student != null) {
            info.setStudentId(student.getStudentId());
            info.getIdentity().add("student");
        }
        Teacher teacher = userMapper.getTeacherByUserId(userId);
        if (teacher != null) {
            info.setTeacherId(teacher.getTeacherId());
            info.getIdentity().add("teacher");
        }
        Administrator administrator = userMapper.getAdministratorByUserId(userId);
        if (administrator != null) {
            info.setAdministratorId(administrator.getAdministratorId());
            info.getIdentity().add("administrator");
        }
        return info;
    }

    @Override
    public void addUser(String username, String password, String nickname) {
        userMapper.addUser(snowflake.nextPrefixId(PrefixSnowflake.PREFIX_USER), username, password, nickname);
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return userMapper.checkAccessToken(accessToken) != null;
    }

    /**
     * 为用户添加新身份
     * @param identity 待添加的新身份
     */
    @Override
    public void addIdentity(String userId, List<String> identity) {
        for (String i : identity) {
            if (i.equals("student")) {
                if (userMapper.getStudentByUserId(userId) == null) {
                    userMapper.addStudent(snowflake.nextPrefixId(PrefixSnowflake.PREFIX_STUDENT), userId);
                }
            }
            if (i.equals("teacher")) {
                if (userMapper.getTeacherByUserId(userId) == null) {
                    userMapper.addTeacher(snowflake.nextPrefixId(PrefixSnowflake.PREFIX_TEACHER), userId);
                }
            }
            if (i.equals("administrator")) {
                if (userMapper.getAdministratorByUserId(userId) == null) {
                    userMapper.addAdministrator(snowflake.nextPrefixId(PrefixSnowflake.PREFIX_ADMINISTRATOR), userId, SHA256Util.toSHA256(String.valueOf(snowflake.nextId())));
                }
            }
        }
    }

    @Override
    public Set<String> getUserSet(List<String> userId, List<String> studentId, List<String> teacherId, List<String> administratorId) {
        Set<String> set = new HashSet<>();
        if (userId != null && !userId.isEmpty()) {
            set.addAll(userId);
        }
        if (studentId != null && !studentId.isEmpty()) {
            for (String id : studentId) {
                Student s = userMapper.getStudentByStudentId(id);
                if (s != null) {
                    set.add(s.getUserId());
                }
            }
        }
        if (teacherId != null && !teacherId.isEmpty()) {
            for (String id : teacherId) {
                Teacher t = userMapper.getTeacherByTeacherId(id);
                if (t != null) {
                    set.add(t.getUserId());
                }
            }
        }
        if (administratorId != null && !administratorId.isEmpty()) {
            for (String id : administratorId) {
                Administrator a = userMapper.getAdministratorByAdministratorId(id);
                if (a != null) {
                    set.add(a.getUserId());
                }
            }
        }
        return set;
    }

    @Override
    public void removeUser(String userId) {
        userMapper.removeUser(userId);
    }

    @Override
    public void modifyNickname(String userId, String nickname) {
        userMapper.modifyNickName(userId, nickname);
    }

    @Override
    public List<User> searchUser(String keyword) {
        return userMapper.searchUser("%" + keyword + "%");
    }


}
