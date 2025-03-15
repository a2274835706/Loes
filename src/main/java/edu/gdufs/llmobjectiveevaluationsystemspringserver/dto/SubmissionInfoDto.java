package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionInfoDto {

    private String submissionId;
    private String releaseId;
    private String studentId;
    private String questionId;
    private String process;
    private String answer;
    private int score;
    private String feedback;

}
