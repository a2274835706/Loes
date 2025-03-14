package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionInfoDto {

    private long questionId;
    private long studentId;
    private String process;
    private String answer;

}
