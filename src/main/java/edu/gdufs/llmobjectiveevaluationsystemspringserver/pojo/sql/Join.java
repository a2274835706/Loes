package edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Join {
    private long classId;
    private long studentId;
    private LocalDateTime joinAt;
    private LocalDateTime exitAt;
    private LocalDateTime fileAt;
}
