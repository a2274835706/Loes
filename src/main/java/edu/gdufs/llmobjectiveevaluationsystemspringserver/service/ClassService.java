package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;


import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;

import java.util.List;
import java.util.Map;

public interface ClassService {

    String addClass(String courseId, String className, String state);

    Class getClassInfoByClassId(String classId);

    List<Class> classInfo(List<String> classId);

    Map<String, List<Class>> classList(List<String> courseId);

    Map<String, List<String>> students(List<String> classId);

    List<String> joinClass(List<String> studentId, String classId);

    boolean removeClass(String classId);

    boolean modifyClass(String classId, String className);


}
