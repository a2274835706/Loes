package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface AssignmentService {

    long addAssignment(long courseId, long teacherId, String title, String description, LocalDateTime deadline);

    List<Assignment> assignmentInfo(List<Long> assignmentId);

    Map<Long, List<Assignment>> assignmentOfCourse(List<Long> courseId);

    Map<Long, List<Assignment>> assignmentOfTeacher(List<Long> teacherId);

    boolean deleteAssignment(long assignmentId);

    boolean updateAssignment(long assignmentId, String title, String description, LocalDateTime deadline);

}
