package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentInfoDto {

    private String assignmentId;
    private String teacherId;
    private String title;
    private String description;

}
