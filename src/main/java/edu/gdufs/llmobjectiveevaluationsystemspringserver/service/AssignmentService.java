package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentQuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.AssignmentQuestion;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.ReleaseAssignment;

import java.util.List;
import java.util.Map;

public interface AssignmentService {

    String addAssignment(String teacherID, String title, String description);

    List<Assignment> assignmentInfo(List<String> assignmentID);

    Map<String, List<Assignment>> assignmentList(List<String> teacherId);

    boolean deleteAssignment(String assignmentID);

    boolean updateAssignment(String assignmentID, String title, String description);

    Map<String, String> addAssignmentQuestion(String assignmentID, List<AssignmentQuestionInfoDto> aqDto);

    boolean deleteAssignmentQuestion(String assignmentQuestionID);

    Map<String, List<AssignmentQuestion>> assignmentQuestionList(List<String> assignmentID);

    List<String> updateAssignmentQuestion(List<AssignmentQuestionInfoDto> dto);

    Map<String, String> addRelease(List<String> classId, String assignmentId, String releaseName, String description, String deadline);

    boolean deleteRelease(String releaseID);

    boolean updateRelease(String releaseID, String releaseName, String description, String deadline);

    List<ReleaseAssignment> releaseAssignmentInfo(List<String> releaseID);

    Map<String, List<ReleaseAssignment>> releaseAssignmentOfAssignment(List<String> assignmentID);

    Map<String, List<ReleaseAssignment>> releaseAssignmentOfClass(List<String> classId);

}
