package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassInfo {
    private long classId;
    private long courseId;
    private String className;
    private String state;
}
