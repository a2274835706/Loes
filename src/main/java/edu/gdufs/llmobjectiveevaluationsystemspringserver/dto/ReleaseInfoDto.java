package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseInfoDto {

    private String releaseId;
    private List<String> classId;
    private String assignmentId;
    private String releaseName;
    private String description;
    private String deadline;

}
