package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.QuestionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.SubmissionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Submission;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public boolean addSubmission(long questionId, long studentId, String process, String answer) {
        if (questionMapper.questionInfo(questionId) != null && userMapper.getStudentByStudentId(studentId) != null) {
            submissionMapper.addSubmission(questionId, studentId, process, answer);
            return true;
        }
        return false;
    }

    @Override
    public Submission submissionInfo(long questionId, long studentId) {
        return submissionMapper.submissionInfo(questionId, studentId);
    }

    @Override
    public Map<Long, List<Submission>> submissionOfStudent(List<Long> studentId) {
        Map<Long, List<Submission>> map = new HashMap<>();
        for (long id : studentId) {
            map.put(id, submissionMapper.submissionOfStudent(id));
        }
        return map;
    }

    @Override
    public Map<Long, List<Submission>> submissionOfQuestion(List<Long> questionId) {
        Map<Long, List<Submission>> map = new HashMap<>();
        for (long id : questionId) {
            map.put(id, submissionMapper.submissionOfQuestion(id));
        }
        return map;
    }

    @Override
    public boolean modifySubmission(long questionId, long studentId, String process, String answer) {
        if (questionMapper.questionInfo(questionId) != null && userMapper.getStudentByStudentId(studentId) != null) {
            submissionMapper.updateSubmission(questionId, studentId, process, answer);
            return true;
        }
        return false;
    }

    @Override
    public boolean correctAnswer(long questionId, long studentId, int score, String feedback) {
        if (questionMapper.questionInfo(questionId) != null && userMapper.getStudentByStudentId(studentId) != null) {
            submissionMapper.correct(questionId, studentId, score, feedback);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteSubmission(long questionId, long studentId) {
        if (questionMapper.questionInfo(questionId) != null && userMapper.getStudentByStudentId(studentId) != null) {
            submissionMapper.deleteSubmission(questionId, studentId);
            return true;
        }
        return false;
    }
}
