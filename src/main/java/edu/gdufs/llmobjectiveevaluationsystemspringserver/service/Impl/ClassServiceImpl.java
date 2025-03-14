package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.ClassMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.ClassService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.PrefixSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    private ClassMapper classMapper;

    @Autowired
    private PrefixSnowflake snowflake;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String addClass(String courseId, String className, String state) {
        String classId = snowflake.nextPrefixId(PrefixSnowflake.PREFIX_CLASS);
        classMapper.addClass(classId, courseId, className, state);
        return classId;
    }

    @Override
    public List<Class> classInfo(List<String> classId) {
        List<Class> classList = new ArrayList<>();
        for (String id : classId) {
            Class c = classMapper.classInfo(id);
            if (c != null) {
                classList.add(c);
            }
        }
        return classList;
    }


    @Override
    public Map<String, List<Class>> classList(List<String> courseId) {
        Map<String, List<Class>> map = new HashMap<>();
        for (String id: courseId) {
            map.put(id, classMapper.classList(id));
        }
        return map;
    }

    @Override
    public Map<String, List<String>> students(List<String> classId) {
        Map<String, List<String>> map = new HashMap<>();
        for (String id: classId) {
            map.put(id, classMapper.students(id));
        }
        return map;
    }

    @Override
    public List<String> joinClass(List<String> studentId, String classId) {
        List<String> success = new ArrayList<>();
        for (String id : studentId) {
            if (userMapper.getStudentByStudentId(id) != null && classMapper.checkJoin(classId, id) == null) {
                classMapper.joinClass(classId, id);
                success.add(id);
            }
        }
        return success;
    }

    @Override
    public boolean removeClass(String classId) {
        if (classMapper.classInfo(classId) != null) {
            classMapper.removeClass(classId);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyClass(String classId, String className) {
        if (classMapper.classInfo(classId) != null) {
            classMapper.modifyClass(classId, className);
            return true;
        }
        return false;
    }

}
