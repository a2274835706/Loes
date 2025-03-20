package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.ClassInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response.UserInfo;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Class;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Course;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.ClassService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.CourseService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.UserService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * 添加班级
     *
     * @return {@link NormalResult}
     */
    @PostMapping("/add")
    public NormalResult<?> addClass(@RequestBody ClassInfoDto dto) {
        Course course = courseService.courseInfo(dto.getCourseId());
        if (course != null) {
            String classId = classService.addClass(dto.getCourseId(), dto.getClassName(), course.getState());
            return NormalResult.success(classId);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 获取课程的班级信息
     *
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
     *
     * @return {@link NormalResult}
     */
    @GetMapping("/students")
    public NormalResult<?> students(@RequestParam("classId") List<String> classId) {
        return NormalResult.success(classService.students(classId));
    }

    /**
     * 学生加入班级
     *
     * @return {@link NormalResult}
     */
    @GetMapping("/join")
    public NormalResult<?> joinClass(@RequestParam("studentId") List<String> studentId,
                                     @RequestParam("classId") String classId) {
        if (!classService.classInfo(List.of(classId)).isEmpty()) {
            return NormalResult.success(classService.joinClass(studentId, classId));
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 修改班级信息
     *
     * @return {@link NormalResult}
     */
    @PatchMapping("/modify")
    public NormalResult<?> modifyClass(@RequestBody ClassInfoDto dto, HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //如果令牌为空，返回错误信息
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        //获取令牌里的身份
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        if (identity.contains("administrator")) {
            if (classService.modifyClass(dto.getClassId(), dto.getClassName())) {
                return NormalResult.success();
            }
        }
        if (identity.contains("teacher")) {
            //获取当前用户id
            String userId = (String) jwtUtil.verifyToken(token).get("userId");
            //根据用户信息获取用户信息
            UserInfo userInfo = userService.getUserInfo(userId);
            //从用户信息里获取教师id
            String teacherId = userInfo.getTeacherId();
            //获取课程id
            String courseId = classService.getClassInfoByClassId(dto.getClassId()).getCourseId();
            //获取该课程对应的教师列表
            Map<String, List<String>> teacherCourse = courseService.teachers(List.of(courseId));
            //如果教师列表包含当前教师id，则修改班级信息
            if (teacherCourse.get(courseId).contains(teacherId)) {
                if (classService.modifyClass(dto.getClassId(), dto.getClassName())) {
                    return NormalResult.success();
                }
            }
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    /**
     * 删除班级
     *
     * @return {@link NormalResult}
     */
    @DeleteMapping("/remove")
    public NormalResult<?> removeClassByClassId(@RequestParam("classId") String classId, HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //如果令牌为空，返回错误信息
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        //获取令牌里的身份
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        //根据classId获取班级信息
        Class classInfo = classService.getClassInfoByClassId(classId);
        //如果班级信息为空，返回错误信息
        if (classInfo == null) {
            return NormalResult.error(NormalResult.IDENTIFICATION_ERROR);
        }
        //如果身份是管理员，则删除班级
        if (identity.contains("administrator")) {
            if (classService.removeClass(classId)) {
                return NormalResult.success();
            }
        }
        //如果身份是教师
        if (identity.contains("teacher")) {
            //获取当前用户id
            String userId = (String) jwtUtil.verifyToken(token).get("userId");
            //根据用户信息获取用户信息
            UserInfo userInfo = userService.getUserInfo(userId);
            //从用户信息里获取教师id
            String teacherId = userInfo.getTeacherId();
            //获取课程id
            String courseId = classInfo.getCourseId();
            //如果教师id在课程的教师列表里，则删除班级
            Map<String, List<String>> teacherList = courseService.teachers(List.of(courseId));
            if (teacherList.get(courseId).contains(teacherId)) {
                if (classService.removeClass(classId)) {
                    return NormalResult.success();
                }
            }
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }
}
