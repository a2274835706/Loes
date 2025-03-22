package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.CourseNotice;

import java.util.List;
import java.util.Map;

public interface CourseService {

    String addCourse(String courseName, String description);

    boolean updateState(String courseId, String state);

    boolean addTeach(String courseId, String teacherId);

    boolean addStudy(String courseId, String studentId);

    Course courseInfo(String courseId);

    Map<String, Course> courseList(List<String> courseId);

    Map<String, List<String>> studentCourseList(List<String> studentId);

    Map<String, List<String>> teacherCourseList(List<String> teacherId);

    Map<String, List<String>> teachers(List<String> courseId);

    boolean teacherFileCourse(String courseId);

    boolean modifyCourse(String courseId, String courseName, String description);

    List<Course> searchCourse(String courseName);

    boolean removeCourse(String courseId);

    boolean checkTeach(String courseId, String teacherId);

    CourseNotice getCourseNoticeById(String courseNoticeId);

    void addNotice(String courseNoticeId, String courseId, String teacherId, String title, String content);


    void modifyNotice(String courseNoticeId, String courseId, String title, String content);

    List<CourseNotice> searchCourseNotice(String keyword);

    boolean removeNotice(String courseNoticeId);
}
