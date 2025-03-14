package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoDto {

    private long questionId;
    private long assignmentId;
    private String content;
    private int score;
    private int sortOrder;
    private String questionType;

}
