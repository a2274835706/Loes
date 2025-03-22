package edu.gdufs.llmobjectiveevaluationsystemspringserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseNoticeDto {
    private String courseNoticeId;
    private String courseId;
    private String teacherId;
    private String title;
    private String content;
}
