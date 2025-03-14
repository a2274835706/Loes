package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.ClassMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.CourseMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.CourseService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private PrefixSnowflake snowflake;

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String addCourse(String courseName, String description) {
        String courseId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_COURSE);
        courseMapper.addCourse(courseId, courseName, description);
        return courseId;
    }

    @Override
    public boolean updateState(String courseId, String state) {
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
    public boolean addTeach(String courseId, String teacherId) {
        if (courseInfo(courseId) != null && userMapper.getTeacherByTeacherId(teacherId) != null) {
            if (courseMapper.checkTeach(courseId, teacherId) == null) {
                courseMapper.addTeach(courseId, teacherId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addStudy(String courseId, String studentId) {
        if (courseInfo(courseId) != null && userMapper.getStudentByStudentId(studentId) != null) {
            if (courseMapper.checkStudy(courseId, studentId) == null) {
                courseMapper.addStudy(courseId, studentId);
                return true;
            }
        }
        return false;
    }

    @Override
    public Course courseInfo(String courseId) {
        return courseMapper.courseInfo(courseId);
    }

    @Override
    public Map<String, Course> courseList(List<String> courseId) {
        Map<String, Course> courseList = new HashMap<>();
        for (String id : courseId) {
            courseList.put(id, courseMapper.courseInfo(id));
        }
        return courseList;
    }

    @Override
    public Map<String, List<String>> studentCourseList(List<String> studentId) {
        Map<String, List<String>> map = new HashMap<>();
        for (String id : studentId) {
            map.put(id, courseMapper.getStudyList(id));
        }
        return map;
    }

    @Override
    public Map<String, List<String>> teacherCourseList(List<String> teacherId) {
        Map<String, List<String>> map = new HashMap<>();
        for (String id : teacherId) {
            map.put(id, courseMapper.getTeachList(id));
        }
        return map;
    }

    @Override
    public Map<String, List<String>> teachers(List<String> teacherId) {
        Map<String, List<String>> map = new HashMap<>();
        for (String id : teacherId) {
            map.put(id, courseMapper.getTeachers(id));
        }
        return map;
    }

    @Override
    public boolean teacherFileCourse(String courseId) {
        if (courseInfo(courseId) != null) {
            courseMapper.file(courseId);
            classMapper.fileForCourse(courseId);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyCourse(String courseId, String courseName, String description) {
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
    public boolean removeCourse(String courseId) {
        if (courseInfo(courseId) != null) {
            courseMapper.removeCourse(courseId);
            return true;
        }
        return false;
    }

}
