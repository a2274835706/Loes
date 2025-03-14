package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    private long questionId;
    private long assignmentId;
    private String content;
    private int score;
    private int sortOrder;
    private String questionType;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
