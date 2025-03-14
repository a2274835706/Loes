package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    long addQuestion(long assignmentId, String content, int score, int sortOrder, String questionType);

    List<Question> questionInfo(List<Long> questionId);

    Map<Long, List<Question>> questionList(List<Long> assignmentId);

    boolean deleteQuestion(long questionId);

    boolean updateQuestion(long questionId, String content, int score, int sortOrder, String questionType);

}
