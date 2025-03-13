package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {

    long addCourse(String courseName, String description);
    boolean updateState(long courseId, String state);
    boolean addTeach(long courseId, long teacherId);
    boolean addStudy(long courseId, long studentId);
    Course courseInfo(long courseId);
    Map<Long, Course> courseList(List<Long> courseId);
    Map<Long, List<Long>> studentCourseList(List<Long> studentId);
    Map<Long, List<Long>> teacherCourseList(List<Long> teacherId);
    Map<Long, List<Long>> teachers(List<Long> teacherId);
    boolean teacherFileCourse(long courseId);
    boolean modifyCourse(long courseId, String courseName, String description);
    List<Course> searchCourse(String courseName);
    boolean removeCourse(long courseId);


}
