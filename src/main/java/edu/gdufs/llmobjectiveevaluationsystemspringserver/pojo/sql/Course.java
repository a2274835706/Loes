package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private String courseId;
    private String courseName;
    private String description;
    private String state;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime fileAt;

}
