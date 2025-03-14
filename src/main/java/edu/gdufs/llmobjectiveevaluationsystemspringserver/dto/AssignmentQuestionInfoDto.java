package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentQuestionInfoDto {

    private String assignmentQuestionId;
    private String questionId;
    private int score;
    private int soreOrder;

}
