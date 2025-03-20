package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentQuestion {

    private String assignmentQuestionId;
    private String assignmentId;
    private String questionId;
    private int score;
    private int sortOrder;

}
