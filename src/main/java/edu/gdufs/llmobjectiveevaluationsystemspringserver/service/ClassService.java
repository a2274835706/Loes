package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;


import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.ClassNotice;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;

import java.util.List;
import java.util.Map;

public interface ClassService {

    String addClass(String courseId, String className, String state);

    Class getClassInfoByClassId(String classId);

    ClassNotice getClassNoticeById(String classNoticeId);

    List<Class> classInfo(List<String> classId);

    List<String> getClassByStudentId(String studentId);

    List<String> getCourseByClassId(String classId);

    Map<String, List<Class>> classList(List<String> courseId);

    Map<String, List<String>> students(List<String> classId);

    List<String> joinClass(List<String> studentId, String classId);

    boolean removeClass(String classId);

    boolean modifyClass(String classId, String className);

    void addClassNotice(String classNoticeId, String classId, String teacherId, String title, String content);

    void updateClassNotice(String classNoticeId, String title, String content);

    String getTeacherByClassNoticeId(String classNoticeId);

    void deleteNotice(String classNoticeId);

    List<ClassNotice> searchClassNotice(String keyword);
}
