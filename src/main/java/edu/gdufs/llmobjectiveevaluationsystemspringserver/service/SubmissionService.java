package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Submission;

import java.util.List;
import java.util.Map;

public interface SubmissionService {

    String addSubmission(String releaseId, String studentId, String questionId, String process, String answer);

    List<Submission> submissionInfo(List<String> submissionId);

    Map<String, Map<String, List<Submission>>> submissionOfRelease(List<String> releaseId);

    Map<String, Map<String, List<Submission>>> submissionOfStudent(List<String> studentId);

    Map<String, List<Submission>> submissionOfStudentOfReleases(String studentId, List<String> releaseId);

    Map<String, List<Submission>> submissionOfReleaseOfStudents(List<String> studentId, String releaseId);

    boolean deleteSubmission(String submissionId);

    boolean updateSubmission(String submissionId, String process, String answer);

    boolean correctSubmission(String submissionId, int score, String feedback);

}
