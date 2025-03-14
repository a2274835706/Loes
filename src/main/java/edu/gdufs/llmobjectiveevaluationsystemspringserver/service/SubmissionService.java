package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Submission;

import java.util.List;
import java.util.Map;

public interface SubmissionService {

    boolean addSubmission(long questionId, long studentId, String process, String answer);

    Submission submissionInfo(long questionId, long studentId);

    Map<Long, List<Submission>> submissionOfStudent(List<Long> studentId);

    Map<Long, List<Submission>> submissionOfQuestion(List<Long> questionId);

    boolean modifySubmission(long questionId, long studentId, String process, String answer);

    boolean correctAnswer(long questionId, long studentId, int score, String feedback);

    boolean deleteSubmission(long questionId, long studentId);


}
