package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassInfoDto {

    private String classId;
    private String courseId;
    private String className;

}
