package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assignment {

    private long assignmentId;
    private long courseId;
    private long teacherId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
