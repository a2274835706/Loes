package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private String userid;
    private String studentId;
    private String teacherId;
    private String administratorId;
    private String username;
    private String nickname;
    private List<String> identity;
    private String token;

}
