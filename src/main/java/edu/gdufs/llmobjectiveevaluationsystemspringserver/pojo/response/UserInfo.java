package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private long userid;
    private long studentId;
    private long teacherId;
    private long administratorId;
    private String username;
    private String nickname;
    private List<String> identity;
    private String token;

}
