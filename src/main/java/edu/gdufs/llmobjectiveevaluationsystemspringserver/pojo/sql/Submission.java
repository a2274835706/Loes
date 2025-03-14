package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission {

    private long questionId;
    private long studentID;
    private String process;
    private String answer;
    private int score;
    private String feedback;
    private LocalDateTime submitAt;
    private LocalDateTime updateAt;
    private LocalDateTime correctAt;

}
