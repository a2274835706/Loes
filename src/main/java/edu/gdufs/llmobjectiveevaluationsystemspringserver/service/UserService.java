package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response.UserInfo;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User getUser(long userId);
    User getUser(String username);
    UserInfo getUserInfo(long userId);
    void addUser(String username, String password, String nickname);
    boolean checkAccessToken(String accessToken);
    void addIdentity(long userId, List<String> identity);
    Set<Long> getUserSet(List<Long> userId, List<Long> studentId, List<Long> teacherId, List<Long> administratorId);
    void removeUser(long userId);
    void modifyNickname(long userId, String nickname);
    List<User> searchUser(String keyword);

}
