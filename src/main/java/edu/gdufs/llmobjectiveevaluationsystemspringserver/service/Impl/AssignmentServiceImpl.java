package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentQuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.AssignmentMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.QuestionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.AssignmentQuestion;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.AssignmentService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrefixSnowflake snowflake;

    @Override
    public String addAssignment(String teacherID, String title, String description) {
        if (userMapper.getTeacherByTeacherId(teacherID) != null) {
            String assignmentId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_ASSIGNMENT);
            assignmentMapper.addAssignment(assignmentId, teacherID, title, description);
            return assignmentId;
        }
        return null;
    }

    @Override
    public List<Assignment> assignmentInfo(List<String> assignmentID) {
        List<Assignment> list = new ArrayList<>();
        for (String id : assignmentID) {
            Assignment assignment = assignmentMapper.assignmentInfo(id);
            if (assignment != null) {
                list.add(assignment);
            }
        }
        return list;
    }

    @Override
    public Map<String, List<Assignment>> assignmentList(List<String> teacherId) {
        Map<String, List<Assignment>> map = new HashMap<>();
        for (String id : teacherId) {
            map.put(id, assignmentMapper.assignmentOfTeacher(id));
        }
        return map;
    }

    @Override
    public boolean deleteAssignment(String assignmentID) {
        if (assignmentMapper.assignmentInfo(assignmentID) != null) {
            assignmentMapper.deleteAssignment(assignmentID);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAssignment(String assignmentID, String title, String description) {
        if (assignmentMapper.assignmentInfo(assignmentID) != null) {
            assignmentMapper.updateAssignment(assignmentID, title, description);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, String> addAssignmentQuestion(String assignmentID, List<AssignmentQuestionInfoDto> dto) {
        Map<String, String> map = new HashMap<>();
        if (questionMapper.questionInfo(assignmentID) != null) {
            for (AssignmentQuestionInfoDto d : dto) {
                String assignmentQuestionId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_ASSIGNMENT_QUESTION);
                if (questionMapper.questionInfo(assignmentQuestionId) != null) {
                    assignmentMapper.addAssignmentQuestion(assignmentQuestionId, assignmentID, d.getQuestionId(), d.getScore(), d.getSoreOrder());
                    map.put(d.getQuestionId(), assignmentQuestionId);
                }
            }
        }
        return map;
    }

    @Override
    public boolean deleteAssignmentQuestion(String assignmentQuestionID) {
        if (assignmentMapper.assignmentQuestionInfo(assignmentQuestionID) != null) {
            assignmentMapper.removeAssignmentQuestion(assignmentQuestionID);
            return true;
        }
        return false;
    }

    @Override
    public Map<String, List<AssignmentQuestion>> assignmentQuestionList(List<String> assignmentID) {
        Map<String, List<AssignmentQuestion>> map = new HashMap<>();
        for (String id : assignmentID) {
            map.put(id, assignmentMapper.assignmentQuestionList(id));
        }
        return map;
    }


    @Override
    public List<String> updateAssignmentQuestion(List<AssignmentQuestionInfoDto> dto) {
        List<String> list = new ArrayList<>();
        for (AssignmentQuestionInfoDto d : dto) {
            if (assignmentMapper.assignmentQuestionInfo(d.getAssignmentQuestionId()) != null) {
                assignmentMapper.updateAssignmentQuestion(d.getAssignmentQuestionId(), d.getScore(), d.getSoreOrder());
                list.add(d.getAssignmentQuestionId());
            }
        }
        return list;
    }
}
