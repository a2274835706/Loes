package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import cn.hutool.core.lang.Snowflake;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.ClassMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.CourseMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.ClassService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public long addCourse(String courseName, String description) {
        long courseId = snowflake.nextId();
        courseMapper.addCourse(courseId, courseName, description);
        return courseId;
    }

    @Override
    public boolean updateState(long courseId, String state) {
        if (state.equals("not-started") || state.equals("active")) {
            if (courseInfo(courseId) != null) {
                courseMapper.updateState(courseId, state);
                classMapper.updateStateForCourse(courseId, state);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addTeach(long courseId, long teacherId) {
        if (courseInfo(courseId) != null && userMapper.getTeacherByTeacherId(teacherId) != null) {
            if (courseMapper.checkTeach(courseId, teacherId) == null) {
                courseMapper.addTeach(courseId, teacherId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addStudy(long courseId, long studentId) {
        if (courseInfo(courseId) != null && userMapper.getStudentByStudentId(studentId) != null) {
            if (courseMapper.checkStudy(courseId, studentId) == null) {
                courseMapper.addStudy(courseId, studentId);
                return true;
            }
        }
        return false;
    }

    @Override
    public Course courseInfo(long courseId) {
        return courseMapper.courseInfo(courseId);
    }

    @Override
    public Map<Long, Course> courseList(List<Long> courseId) {
        Map<Long, Course> courseList = new HashMap<>();
        for (Long id : courseId) {
            courseList.put(id, courseMapper.courseInfo(id));
        }
        return courseList;
    }

    @Override
    public Map<Long, List<Long>> studentCourseList(List<Long> studentId) {
        Map<Long, List<Long>> map = new HashMap<>();
        for (Long id : studentId) {
            map.put(id, courseMapper.getStudyList(id));
        }
        return map;
    }

    @Override
    public Map<Long, List<Long>> teacherCourseList(List<Long> teacherId) {
        Map<Long, List<Long>> map = new HashMap<>();
        for (Long id : teacherId) {
            map.put(id, courseMapper.getTeachList(id));
        }
        return map;
    }

    @Override
    public Map<Long, List<Long>> teachers(List<Long> teacherId) {
        Map<Long, List<Long>> map = new HashMap<>();
        for (Long id : teacherId) {
            map.put(id, courseMapper.getTeachers(id));
        }
        return map;
    }

    @Override
    public boolean teacherFileCourse(long courseId) {
        if (courseInfo(courseId) != null) {
            courseMapper.file(courseId);
            classMapper.fileForCourse(courseId);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyCourse(long courseId, String courseName, String description) {
        if (courseInfo(courseId) != null) {
            courseMapper.updateInfo(courseId, courseName, description);
            return true;
        }
        return false;
    }

    @Override
    public List<Course> searchCourse(String courseName) {
        return courseMapper.search("%" + courseName + "%");
    }

    @Override
    public boolean removeCourse(long courseId) {
        if (courseInfo(courseId) != null) {
            courseMapper.removeCourse(courseId);
            return true;
        }
        return false;
    }

}
