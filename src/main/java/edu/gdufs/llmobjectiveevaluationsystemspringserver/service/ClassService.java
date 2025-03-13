package edu.gdufs.llmobjectiveevaluationsystemspringserver.service;


import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;

import java.util.List;
import java.util.Map;

public interface ClassService {

    long addClass(long courseId, String className, String state);

    Class classInfo(long classId);

    Map<Long, List<Class>> classList(List<Long> courseId);

    Map<Long, List<Long>> students(List<Long> classId);

    List<Long> joinClass(List<Long> studentId, long classId);

    boolean removeClass(long classId);

    boolean modifyClass(long classId, String className);


}
