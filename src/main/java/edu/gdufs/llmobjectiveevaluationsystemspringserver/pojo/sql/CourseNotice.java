package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseNotice {
    private String courseNoticeId;
    private String courseId;
    private String teacherId;
    private String title;
    private String content;
    private LocalDate createAt;
    private LocalDate updateAt;
}
