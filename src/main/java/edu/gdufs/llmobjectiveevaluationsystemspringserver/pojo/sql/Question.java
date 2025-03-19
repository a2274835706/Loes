package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private String questionId;
    private String teacherId;
    private String content;
    private String answer;
    private String questionType;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
