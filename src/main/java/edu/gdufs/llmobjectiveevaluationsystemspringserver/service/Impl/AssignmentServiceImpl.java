package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentQuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.AssignmentMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.ClassMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.QuestionMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.AssignmentQuestion;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.ReleaseAssignment;
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
    private ClassMapper classMapper;

    @Autowired
    private PrefixSnowflake snowflake;

    @Override
    public String addAssignment(String teacherId, String title, String description) {
        if (userMapper.getTeacherByTeacherId(teacherId) != null) {
            String assignmentId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_ASSIGNMENT);
            assignmentMapper.addAssignment(assignmentId, teacherId, title, description);
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
    public Map<String, String> addAssignmentQuestion(String assignmentId, List<AssignmentQuestionInfoDto> dto) {
        Map<String, String> map = new HashMap<>();
        if (assignmentMapper.assignmentInfo(assignmentId) != null) {
            for (AssignmentQuestionInfoDto d : dto) {
                String assignmentQuestionId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_ASSIGNMENT_QUESTION);
                if (questionMapper.questionInfo(d.getQuestionId()) != null) {
                    assignmentMapper.addAssignmentQuestion(assignmentQuestionId, assignmentId, d.getQuestionId(), d.getScore(), d.getSortOrder());
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
                assignmentMapper.updateAssignmentQuestion(d.getAssignmentQuestionId(), d.getScore(), d.getSortOrder());
                list.add(d.getAssignmentQuestionId());
            }
        }
        return list;
    }

    @Override
    public Map<String, String> addRelease(List<String> classId, String assignmentId, String releaseName, String description, String deadline) {
        Map<String, String> map = new HashMap<>();
        for (String id : classId) {
            if (classMapper.classInfo(id) != null) {
                String releaseId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_RELEASE_ASSIGNMENT);
                assignmentMapper.addRelease(releaseId, id, assignmentId, releaseName, description, deadline);
                map.put(id, releaseId);
            }
        }
        return map;
    }

    @Override
    public boolean deleteRelease(String releaseID) {
        if (assignmentMapper.releaseInfo(releaseID) != null) {
            assignmentMapper.deleteRelease(releaseID);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateRelease(String releaseID, String releaseName, String description, String deadline) {
        if (assignmentMapper.releaseInfo(releaseID) != null) {
            assignmentMapper.updateRelease(releaseID, releaseName, description, deadline);
            return true;
        }
        return false;
    }

    @Override
    public List<ReleaseAssignment> releaseAssignmentInfo(List<String> releaseID) {
        List<ReleaseAssignment> list = new ArrayList<>();
        for (String id : releaseID) {
            list.add(assignmentMapper.releaseInfo(id));
        }
        return list;
    }

    @Override
    public Map<String, List<ReleaseAssignment>> releaseAssignmentOfAssignment(List<String> assignmentID) {
        Map<String, List<ReleaseAssignment>> map = new HashMap<>();
        for (String id : assignmentID) {
            map.put(id, assignmentMapper.releaseListOfAssignment(id));
        }
        return map;
    }

    @Override
    public Map<String, List<ReleaseAssignment>> releaseAssignmentOfClass(List<String> classId) {
        Map<String, List<ReleaseAssignment>> map = new HashMap<>();
        for (String id : classId) {
            map.put(id, assignmentMapper.releaseListOfClass(id));
        }
        return map;
    }
}
