package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentQuestionInfo {

    private String questionId;
    private String teacherId;
    private String content;
    private String answer;
    private String questionType;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private String teacherName;
    private int score;
    private int sortOrder;


}
