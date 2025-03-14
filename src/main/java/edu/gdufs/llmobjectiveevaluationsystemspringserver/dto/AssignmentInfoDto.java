package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentInfoDto {

    private long assignmentId;
    private long courseId;
    private long teacherId;
    private String title;
    private String description;
    private long deadline;

}
