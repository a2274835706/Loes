package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response.UserInfo;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    User getUser(String userId);

    User getUserByUsername(String username);

    UserInfo getUserInfo(String userId);

    void addUser(String username, String password, String nickname);

    boolean checkAccessToken(String accessToken);

    void addIdentity(String userId, List<String> identity);

    Set<String> getUserSet(List<String> userId, List<String> studentId, List<String> teacherId, List<String> administratorId);

    void removeUser(String userId);

    void modifyNickname(String userId, String nickname);

    List<User> searchUser(String keyword);

}
