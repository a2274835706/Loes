package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.AssignmentMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.QuestionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.SubmissionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Submission;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.SubmissionService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private PrefixSnowflake snowflake;

    @Override
    public String addSubmission(String releaseId, String studentId, String questionId, String process, String answer) {
        if (assignmentMapper.releaseInfo(releaseId) != null &&
                questionMapper.questionInfo(questionId) != null &&
                userMapper.getStudentByStudentId(studentId) != null) {
            String submissionId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_SUBMISSION);
            submissionMapper.addSubmission(submissionId, releaseId, studentId, questionId, process, answer);
            return submissionId;
        }
        return null;
    }

    @Override
    public List<Submission> submissionInfo(List<String> submissionId) {
        List<Submission> list = new ArrayList<>();
        for (String id: submissionId) {
            Submission submission = submissionMapper.submissionInfo(id);
            if (submission != null) {
                list.add(submission);
            }
        }
        return list;
    }

    @Override
    public Map<String, Map<String, List<Submission>>> submissionOfRelease(List<String> releaseId) {
        Map<String, Map<String, List<Submission>>> map = new HashMap<>();
        for (String id: releaseId) {
            List<Submission> submissions = submissionMapper.submissionOfRelease(id);
            Map<String, List<Submission>> submissionMap = new HashMap<>();
            for (Submission submission: submissions) {
                String studentId = submission.getStudentId();
                if (!submissionMap.containsKey(studentId)) {
                    submissionMap.put(studentId, new ArrayList<>());
                }
                submissionMap.get(studentId).add(submission);
            }
            map.put(id, submissionMap);
        }
        return map;
    }

    @Override
    public Map<String, Map<String, List<Submission>>> submissionOfStudent(List<String> studentId) {
        Map<String, Map<String, List<Submission>>> map = new HashMap<>();
        for (String id: studentId) {
            List<Submission> submissions = submissionMapper.submissionOfStudent(id);
            Map<String, List<Submission>> submissionMap = new HashMap<>();
            for (Submission submission: submissions) {
                String releaseId = submission.getReleaseId();
                if (!submissionMap.containsKey(releaseId)) {
                    submissionMap.put(releaseId, new ArrayList<>());
                }
                submissionMap.get(releaseId).add(submission);
            }
            map.put(id, submissionMap);
        }
        return map;
    }

    @Override
    public Map<String, List<Submission>> submissionOfStudentOfReleases(String studentId, List<String> releaseId) {
        Map<String, List<Submission>> map = new HashMap<>();
        for (String id: releaseId) {
            map.put(id, submissionMapper.submissionOfReleaseAndStudent(id, studentId));
        }
        return map;
    }

    @Override
    public Map<String, List<Submission>> submissionOfReleaseOfStudents(List<String> studentId, String releaseId) {
        Map<String, List<Submission>> map = new HashMap<>();
        for (String id: studentId) {
            map.put(id, submissionMapper.submissionOfReleaseAndStudent(releaseId, id));
        }
        return map;
    }

    @Override
    public boolean deleteSubmission(String submissionId) {
        if (submissionMapper.submissionInfo(submissionId) != null) {
            submissionMapper.deleteSubmission(submissionId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSubmission(String submissionId, String process, String answer) {
        if (submissionMapper.submissionInfo(submissionId) != null) {
            submissionMapper.updateSubmission(submissionId, process, answer);
            return true;
        }
        return false;
    }

    @Override
    public boolean correctSubmission(String submissionId, int score, String feedback) {
        if (submissionMapper.submissionInfo(submissionId) != null) {
            submissionMapper.correctSubmission(submissionId, score, feedback);
            return true;
        }
        return false;
    }
}
