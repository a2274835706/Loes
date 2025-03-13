package edu.gdufs.llmobjectiveevaluationsystemspringserver.service.Impl;

import cn.hutool.core.lang.Snowflake;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.ClassMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.ClassService;
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
    private Snowflake snowflake;

    @Autowired
    private UserMapper userMapper;

    @Override
    public long addClass(long courseId, String className, String state) {
        long classId = snowflake.nextId();
        classMapper.addClass(classId, courseId, className, state);
        return classId;
    }

    @Override
    public Class classInfo(long classId) {
        return classMapper.classInfo(classId);
    }


    @Override
    public Map<Long, List<Class>> classList(List<Long> courseId) {
        Map<Long, List<Class>> map = new HashMap<>();
        for (long id: courseId) {
            map.put(id, classMapper.classList(id));
        }
        return map;
    }

    @Override
    public Map<Long, List<Long>> students(List<Long> classId) {
        Map<Long, List<Long>> map = new HashMap<>();
        for (long id: classId) {
            map.put(id, classMapper.students(id));
        }
        return map;
    }

    @Override
    public List<Long> joinClass(List<Long> studentId, long classId) {
        List<Long> success = new ArrayList<>();
        for (Long id : studentId) {
            if (userMapper.getStudentByStudentId(id) != null && classMapper.checkJoin(classId, id) == null) {
                classMapper.joinClass(classId, id);
                success.add(id);
            }
        }
        return success;
    }

    @Override
    public boolean removeClass(long classId) {
        if (classMapper.classInfo(classId) != null) {
            classMapper.removeClass(classId);
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyClass(long classId, String className) {
        if (classMapper.classInfo(classId) != null) {
            classMapper.modifyClass(classId, className);
            return true;
        }
        return false;
    }

}
