package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassNoticeDto {
    private String classNoticeId;
    private String classId;
    private String teacherId;
    private String title;
    private String content;
}
