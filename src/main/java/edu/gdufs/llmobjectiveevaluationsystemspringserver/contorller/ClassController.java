package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.ClassInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
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
    @PostMapping("/add")
    public NormalResult<?> addClass(@RequestBody ClassInfoDto dto){
        Course course = courseService.courseInfo(dto.getCourseId());
        if (course != null){
            String classId = classService.addClass(dto.getCourseId(), dto.getClassName(), course.getState());
            return NormalResult.success(classId);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 获取课程的班级信息
     * @return {@link NormalResult}
     */
    @GetMapping("/list")
    public NormalResult<?> classList(@RequestParam("courseId") List<String> courseId) {
        return NormalResult.success(classService.classList(courseId));
    }

    @GetMapping("/info")
    public NormalResult<?> classInfo(@RequestParam("classId") List<String> classId) {
        return NormalResult.success(classService.classInfo(classId));
    }

    /**
     * 获取班级学生
     * @return {@link NormalResult}
     */
    @GetMapping("/students")
    public NormalResult<?> students(@RequestParam("classId") List<String> classId){
        return NormalResult.success(classService.students(classId));
    }

    /**
     * 学生加入班级
     * @return {@link NormalResult}
     */
    @GetMapping("/join")
    public NormalResult<?> joinClass(@RequestParam("studentId") List<String> studentId,
                                     @RequestParam("classId") String classId){
        if (!classService.classInfo(List.of(classId)).isEmpty()) {
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
        if(classService.modifyClass(dto.getClassId(), dto.getClassName())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 删除班级
     * @return {@link NormalResult}
     */
    @DeleteMapping("/remove")
    public NormalResult<?> removeClassByClassId(@RequestParam("classId") String classId){
        if( classService.removeClass(classId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }
}
