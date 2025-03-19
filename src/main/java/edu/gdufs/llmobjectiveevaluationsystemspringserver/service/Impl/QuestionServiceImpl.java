package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.QuestionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.QuestionService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrefixSnowflake snowflake;

    @Override
    public String addQuestion(String teacherId, String content, String answer, String questionType) {
        if (userMapper.getTeacherByTeacherId(teacherId) != null) {
            String questionId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_QUESTION);
            questionMapper.addQuestion(questionId, teacherId, content, answer, questionType);
            return questionId;
        }
        return null;
    }

    @Override
    public List<Question> questionInfo(List<String> questionId) {
        List<Question> list = new ArrayList<>();
        for (String id : questionId) {
            Question question = questionMapper.questionInfo(id);
            if (question != null) {
                list.add(question);
            }
        }
        return list;
    }

    @Override
    public Map<String, List<Question>> questionList(List<String> teacherId) {
        Map<String, List<Question>> map = new HashMap<>();
        for (String id : teacherId) {
            map.put(id, questionMapper.questionOfTeacher(id));
        }
        return map;
    }

    @Override
    public boolean deleteQuestion(String questionId) {
        if (questionMapper.questionInfo(questionId) != null) {
            questionMapper.deleteQuestion(questionId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateQuestion(String questionId, String content, String answer, String questionType) {
        if (questionMapper.questionInfo(questionId) != null) {
            questionMapper.updateQuestion(questionId, content, answer, questionType);
            return true;
        }
        return false;
    }

}
