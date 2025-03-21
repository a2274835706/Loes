package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.CourseInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 添加课程
     * @return {@link NormalResult}
     */
    @PostMapping("/add")
    public NormalResult<?> addCourse(@RequestBody CourseInfoDto dto) {
        long courseId = courseService.addCourse(dto.getCourseName(), dto.getDescription());
        return NormalResult.success(courseId);
    }

    /**
     * 学生或教师加入课程
     * <p>1.确定身份，分别处理</p>
     * <p>2.插入数据到{@code teach}或{@code study}表</p>
     * <p>3.返回结果</p>
     * @return {@link NormalResult}
     */
    @GetMapping("{identity}/join")
    public NormalResult<?> joinCourse(@PathVariable("identity") String identity, @RequestParam("courseId") long courseId, @RequestParam("id") List<Long> id) {
        List<Long> success = new ArrayList<>();
        if (identity.equals("teacher")) {
            for (long teacherId : id) {
                if (courseService.addTeach(courseId, teacherId)) {
                    success.add(teacherId);
                }
            }
            return NormalResult.success(success);
        }
        if (identity.equals("student")) {
            for (long studentId : id) {
                if (courseService.addStudy(courseId, studentId)) {
                    success.add(studentId);
                }
            }
            return NormalResult.success(success);
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    /**
     * 查询课程信息
     * @return {@link NormalResult}
     */
    @GetMapping("/info")
    public NormalResult<?> courseInfo(@RequestParam("courseId") List<Long> courseId) {
        return NormalResult.success(courseService.courseList(courseId));
    }

    /**
     * 查询教师或学生的课程
     * @return {@link NormalResult}
     */
    @GetMapping("/{identity}/list")
    public NormalResult<?> courseList(@PathVariable("identity") String identity, @RequestParam("id") List<Long> id) {
        if (identity.equals("teacher")) {
            return NormalResult.success(courseService.teacherCourseList(id));
        }
        if (identity.equals("student")) {
            return NormalResult.success(courseService.studentCourseList(id));
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    /**
     * 获取课程的老师
     * @return {@link NormalResult}
     */
    @GetMapping("/teachers")
    public NormalResult<?> teachers(@RequestParam("courseId") List<Long> courseId) {
        return NormalResult.success(courseService.teachers(courseId));
    }

    /**
     * 更改课程状态，未开始（not-started）或已开始（active）
     * @return {@link NormalResult}
     */
    @PatchMapping("/state")
    public NormalResult<?> updateState(@RequestParam("courseId") long courseId, @RequestParam("state") String state) {
        if (courseService.updateState(courseId, state)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    /**
     * 教师归档课程
     * @return {@link NormalResult}
     */
    @PatchMapping("/file")
    public NormalResult<?> fileCourse(@RequestParam("courseId") long courseId) {
        if (courseService.teacherFileCourse(courseId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    /**
     * 修改课程信息
     * @return {@link NormalResult}
     */
    @PatchMapping("/modify")
    public NormalResult<?> modifyInfo(@RequestBody CourseInfoDto dto) {
        if (courseService.modifyCourse(dto.getCourseId(), dto.getCourseName(), dto.getDescription())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    /**
     * 根据课程名模糊搜索课程
     * @return {@link NormalResult}
     */
    @GetMapping("/search")
    public NormalResult<?> search(@RequestParam("courseName") String keyword) {
        return NormalResult.success(courseService.searchCourse(keyword));
    }

    /**
     * 删除课程
     * @return {@link NormalResult}
     */
    @DeleteMapping("/remove")
    public NormalResult<?> removeCourse(@RequestParam("courseId") long courseId) {
        if (courseService.removeCourse(courseId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

}
