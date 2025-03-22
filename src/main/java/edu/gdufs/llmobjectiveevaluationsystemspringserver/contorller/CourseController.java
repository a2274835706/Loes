package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.CourseInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.CourseNoticeDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.response.UserInfo;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.CourseNotice;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.CourseService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.UserService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    /**
     * 添加课程
     * @return {@link NormalResult}
     */
    @PostMapping("/add")
    public NormalResult<?> addCourse(@RequestBody CourseInfoDto dto) {
        String courseId = courseService.addCourse(dto.getCourseName(), dto.getDescription());
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
    public NormalResult<?> joinCourse(@PathVariable("identity") String identity, @RequestParam("courseId") String courseId, @RequestParam("id") List<String> id) {
        List<String> success = new ArrayList<>();
        if (identity.equals("teacher")) {
            for (String teacherId : id) {
                if (courseService.addTeach(courseId, teacherId)) {
                    success.add(teacherId);
                }
            }
            return NormalResult.success(success);
        }
        if (identity.equals("student")) {
            for (String studentId : id) {
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
    public NormalResult<?> courseInfo(@RequestParam("courseId") List<String> courseId) {
        return NormalResult.success(courseService.courseList(courseId));
    }

    /**
     * 查询教师或学生的课程
     * @return {@link NormalResult}
     */
    @GetMapping("/{identity}/list")
    public NormalResult<?> courseList(@PathVariable("identity") String identity, @RequestParam("id") List<String> id) {
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
    public NormalResult<?> teachers(@RequestParam("courseId") List<String> courseId) {
        return NormalResult.success(courseService.teachers(courseId));
    }

    /**
     * 更改课程状态，未开始（not-started）或已开始（active）
     * @return {@link NormalResult}
     */
    @PatchMapping("/state")
    public NormalResult<?> updateState(@RequestParam("courseId") String courseId, @RequestParam("state") String state) {
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
    public NormalResult<?> fileCourse(@RequestParam("courseId") String courseId) {
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
    public NormalResult<?> modifyInfo(@RequestBody CourseInfoDto dto, HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //如果令牌为空，返回错误信息
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        //获取身份
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        //管理员
        if(identity.contains("administrator")) {
            if (courseService.modifyCourse(dto.getCourseId(), dto.getCourseName(), dto.getDescription())) {
                return NormalResult.success();
            }
        }
        //教师
        if(identity.contains("teacher")){
            //根据当前userId获取教师id
            UserInfo userInfo = userService.getUserInfo((String) jwtUtil.verifyToken(token).get("userId"));
            if(userInfo == null) {
                return NormalResult.error(NormalResult.IDENTIFICATION_ERROR);
            }
            String teacherId = userInfo.getTeacherId();

            //获取课程id
            String courseId = dto.getCourseId();

            //获取该课程对应的教师列表，遍历教师列表，如果包含当前教师id，则修改课程信息
            Map<String, List<String>> teacherList = courseService.teachers(List.of(courseId));
            if(teacherList.get(courseId).contains(teacherId)){
                if(courseService.modifyCourse(dto.getCourseId(), dto.getCourseName(), dto.getDescription())){
                    return NormalResult.success();
                }
            }
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
    public NormalResult<?> removeCourse(@RequestParam("courseId") String courseId, HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //如果令牌为空，返回错误信息
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        //获取身份
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        if (identity.equals("administrator")) {
            if (courseService.removeCourse(courseId)) {
                return NormalResult.success();
            }
        }
        //教师
        if(identity.contains("teacher")) {
            //根据当前userId获取教师id
            UserInfo userInfo = userService.getUserInfo((String) jwtUtil.verifyToken(token).get("userId"));
            if (userInfo == null) {
                return NormalResult.error(NormalResult.IDENTIFICATION_ERROR);
            }
            String teacherId = userInfo.getTeacherId();

            //根据课程id获取教师列表，如果包含当前教师id，则删除课程
            Map<String, List<String>> teacherList = courseService.teachers(List.of(courseId));
            if (teacherList.get(courseId).contains(teacherId)) {
                if (courseService.removeCourse(courseId)) {
                    return NormalResult.success();
                }
            }
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    @PostMapping("/notice")
    public NormalResult<?> addNotice(@RequestBody CourseNoticeDto dto, HttpServletRequest request) {
        //检查公告是否存在
        if(courseService.getCourseNoticeById(dto.getCourseNoticeId()) != null) {
            return NormalResult.error(NormalResult.EXISTENCE_ERROR);
        }
        String token = request.getHeader("Authorization");
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        Teacher teacher = userService.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
        //检查身份，如果是管理员或者当前课程的教师，则添加公告
        if(identity.contains("administrator") || (teacher != null && courseService.checkTeach(dto.getCourseId(), teacher.getTeacherId()))){
            courseService.addNotice(dto.getCourseNoticeId(), dto.getCourseId(), dto.getTeacherId(), dto.getTitle(), dto.getContent());
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

    @PatchMapping("/notice")
    public NormalResult<?> modifyNotice(@RequestBody CourseNoticeDto dto, HttpServletRequest request) {
        System.out.println(dto);
        //检查公告是否存在
        if(courseService.getCourseNoticeById(dto.getCourseNoticeId()) == null) {
            return NormalResult.error(NormalResult.EXISTENCE_ERROR);
        }
        String token = request.getHeader("Authorization");
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        Teacher teacher = userService.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
        //检查身份，如果是管理员或者当前课程的教师，则修改公告
        if(identity.contains("administrator")
                || (teacher != null && courseService.checkTeach(dto.getCourseId(), teacher.getTeacherId()) && dto.getTeacherId().equals(teacher.getTeacherId()))){
            courseService.modifyNotice(dto.getCourseNoticeId(), dto.getCourseId(), dto.getTitle(), dto.getContent());
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

    @GetMapping("/notice")
    public NormalResult<?> searchCourseNotice(@RequestParam("keyword") String keyword) {
        if(keyword == null || keyword.isEmpty()) {
            return NormalResult.error(NormalResult.VALIDATION_ERROR);
        }
        List<CourseNotice> notices = courseService.searchCourseNotice(keyword);
        if(notices != null && !notices.isEmpty()) {
            return NormalResult.success(notices);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @DeleteMapping("/notice")
    public NormalResult<?> removeNotice(@RequestParam("courseNoticeId") String courseNoticeId, HttpServletRequest request) {
        CourseNotice courseNotice = courseService.getCourseNoticeById(courseNoticeId);
        if(courseNotice == null) {
            return NormalResult.error(NormalResult.EXISTENCE_ERROR);
        }
        String token = request.getHeader("Authorization");
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        Teacher teacher = userService.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
        if(identity.contains("administrator") || (teacher != null && courseNotice.getTeacherId().equals(teacher.getTeacherId()))){
            if(courseService.removeNotice(courseNoticeId)) {
                return NormalResult.success();
            }
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

}
