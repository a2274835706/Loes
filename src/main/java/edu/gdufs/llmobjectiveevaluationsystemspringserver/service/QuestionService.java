package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;

import java.util.List;
import java.util.Map;

public interface QuestionService {

    String addQuestion(String teacherId, String content, String answer, String questionType);

    List<Question> questionInfo(List<String> questionId);

    Map<String, List<Question>> questionList(List<String> teacherId);

    boolean deleteQuestion(String questionId);

    boolean updateQuestion(String questionId, String content, String answer, String questionType);

}
