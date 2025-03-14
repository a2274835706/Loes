package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import cn.hutool.core.lang.Snowflake;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.AssignmentMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.CourseMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private Snowflake snowflake;

    @Override
    public long addAssignment(long courseId, long teacherId, String title, String description, LocalDateTime deadline) {
        if (courseMapper.checkTeach(courseId, teacherId) != null) {
            long assignmentId = snowflake.nextId();
            assignmentMapper.addAssignment(assignmentId, courseId, teacherId, title, description, deadline);
            return assignmentId;
        }
        return -1;
    }

    @Override
    public List<Assignment> assignmentInfo(List<Long> assignmentId) {
        List<Assignment> assignments = new ArrayList<>();
        for (long id : assignmentId) {
            assignments.add(assignmentMapper.assignmentInfo(id));
        }
        return assignments;
    }

    @Override
    public Map<Long, List<Assignment>> assignmentOfCourse(List<Long> courseId) {
        Map<Long, List<Assignment>> assignments = new HashMap<>();
        for (long id : courseId) {
            assignments.put(id, assignmentMapper.assignmentOfCourse(id));
        }
        return assignments;
    }

    @Override
    public Map<Long, List<Assignment>> assignmentOfTeacher(List<Long> teacherId) {
        Map<Long, List<Assignment>> assignments = new HashMap<>();
        for (long id : teacherId) {
            assignments.put(id, assignmentMapper.assignmentOfTeacher(id));
        }
        return assignments;
    }

    @Override
    public boolean deleteAssignment(long assignmentId) {
        if (assignmentMapper.assignmentInfo(assignmentId) != null) {
            assignmentMapper.removeAssignment(assignmentId);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAssignment(long assignmentId, String title, String description, LocalDateTime deadline) {
        if (assignmentMapper.assignmentInfo(assignmentId) != null) {
            assignmentMapper.updateAssignment(assignmentId, title, description, deadline);
            return true;
        }
        return false;
    }
}
