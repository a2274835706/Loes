package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseAssignment {

    private String releaseId;
    private String assignmentId;
    private String classId;
    private String releaseName;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime releaseAt;

}
