package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.ClassInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.ClassService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private CourseService courseService;

    /**
     * 添加班级
     * @return {@link NormalResult}
     */
    @PatchMapping("/add")
    public NormalResult<?> addClass(@RequestBody ClassInfoDto dto){
        Course course = courseService.courseInfo(dto.getCourseId());
        if (course != null){
            long classId = classService.addClass(dto.getCourseId(), dto.getClassName(), course.getState());
            return NormalResult.success(classId);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 获取班级信息
     * @return {@link NormalResult}
     */
    @GetMapping("/list")
    public NormalResult<?> getClassInfo(@RequestParam("courseId") List<Long> courseId) {
        return NormalResult.success(classService.classList(courseId));
    }

    /**
     * 获取班级学生
     * @return {@link NormalResult}
     */
    @GetMapping("/students")
    public NormalResult<?> students(@RequestBody List<Long> classId){
        return NormalResult.success(classService.students(classId));
    }

    /**
     * 学生加入班级
     * @return {@link NormalResult}
     */
    @GetMapping("/join")
    public NormalResult<?> joinClass(@RequestParam("studentId") List<Long> studentId,
                                     @RequestParam("classId") long classId){
        Class c = classService.classInfo(classId);
        if (c != null){
            return NormalResult.success(classService.joinClass(studentId, classId));
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 修改班级信息
     * @return {@link NormalResult}
     */
    @PatchMapping("/modify")
    public NormalResult<?> modifyClass(@RequestBody ClassInfoDto dto) {
        if(classService.modifyClass(dto.getCourseId(), dto.getClassName())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 删除班级
     * @return {@link NormalResult}
     */
    @GetMapping("/remove")
    public NormalResult<?> removeClassByClassId(@RequestParam("classId") long classId){
        if( classService.removeClass(classId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }
}
