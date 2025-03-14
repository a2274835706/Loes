package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionInfoDto {

    private String questionId;
    private String teacherId;
    private String content;
    private String answer;
    private String questionType;

}
