package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import cn.hutool.core.lang.Snowflake;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.AssignmentMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.QuestionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public long addQuestion(long assignmentId, String content, int score, int sortOrder, String questionType) {
        if (assignmentMapper.assignmentInfo(assignmentId) != null) {
            long questionId = snowflake.nextId();
            questionMapper.addQuestion(questionId, assignmentId, content, score, sortOrder, questionType);
            return questionId;
        }
        return -1;
    }

    @Override
    public List<Question> questionInfo(List<Long> questionId) {
        List<Question> list = new ArrayList<>();
        for (long id : questionId) {
            list.add(questionMapper.questionInfo(id));
        }
        return list;
    }

    @Override
    public Map<Long, List<Question>> questionList(List<Long> assignmentId) {
        Map<Long, List<Question>> map = new HashMap<>();
        for (long id : assignmentId) {
            map.put(id, questionMapper.questionList(id));
        }
        return map;
    }

    @Override
    public boolean deleteQuestion(long questionId) {
        if (questionMapper.questionInfo(questionId) != null) {
            questionMapper.deleteQuestion(questionId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateQuestion(long questionId, String content, int score, int sortOrder, String questionType) {
        if (questionMapper.questionInfo(questionId) != null) {
            questionMapper.updateQuestion(questionId, content, score, sortOrder, questionType);
            return true;
        }
        return false;
    }

}
