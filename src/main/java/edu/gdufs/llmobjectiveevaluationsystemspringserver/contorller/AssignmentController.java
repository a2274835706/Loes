package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentQuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.AssignmentQuestion;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.AssignmentService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/add")
    public NormalResult<?> addAssignment(@RequestBody AssignmentInfoDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        String userId = (String) jwtUtil.verifyToken(token).get("userId");
        Teacher teacher = userMapper.getTeacherByUserId(userId);
        if(identity.contains("administrator") || (teacher != null && teacher.getTeacherId().equals(dto.getTeacherId()))) {
            return NormalResult.success(assignmentService.addAssignment(dto.getTeacherId(), dto.getTitle(), dto.getDescription()));
        }
        return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
    }

    @GetMapping("/search")
    public NormalResult<?> searchAssignment(@RequestParam("keyword") String keyword, @RequestParam("teacherId") String teacherId) {
        List<Assignment> assignments = assignmentService.searchAssignment(teacherId, keyword);
        if(assignments != null && !assignments.isEmpty()) {
            return NormalResult.success(assignments);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/info")
    public NormalResult<?> assignmentInfo(@RequestParam("assignmentId") List<String> assignmentId) {
        return NormalResult.success(assignmentService.assignmentInfo(assignmentId));
    }

    @GetMapping("/list")
    public NormalResult<?> assignmentList(@RequestParam("teacherId") List<String> teacherId) {
        return NormalResult.success(assignmentService.assignmentList(teacherId));
    }

    @DeleteMapping("/remove")
    public NormalResult<?> removeAssignment(@RequestParam("assignmentId") String assignmentId,HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //如果令牌为空，返回错误信息
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        //获取身份
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        //如果身份是管理员，则删除班级
        if (identity.contains("administrator")) {
            if (assignmentService.deleteAssignment(assignmentId)) {
                return NormalResult.success();
            }
        }
        //如果身份是教师
        if(identity.contains("teacher")) {
            //根据令牌中的用户信息获取教师信息
            Teacher teacher = userMapper.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
            //根据assignmentId获取assignment信息
            List<Assignment> assignments = assignmentService.assignmentInfo(List.of(assignmentId));
            //遍历assignments,如果assignment的teacherId等于当前教师的teacherId，则删除assignment
            for (Assignment assignment : assignments) {
                if (assignment.getTeacherId().equals(teacher.getTeacherId())) {
                    if (assignmentService.deleteAssignment(assignmentId)) {
                        return NormalResult.success();
                    }
                }
            }
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> updateAssignment(@RequestBody AssignmentInfoDto dto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        String userId = (String) jwtUtil.verifyToken(token).get("userId");
        Teacher teacher = userMapper.getTeacherByUserId(userId);
        if(identity.contains("administrator") || (teacher != null && teacher.getTeacherId().equals(dto.getTeacherId()))) {
            if (assignmentService.updateAssignment(dto.getAssignmentId(), dto.getTitle(), dto.getDescription())) {
                return NormalResult.success();
            }
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PostMapping("/question/add")
    public NormalResult<?> addAssignmentQuestion(@RequestParam("assignmentId") String assignmentId,
                                                 @RequestBody List<AssignmentQuestionInfoDto> questions) {
        return NormalResult.success(assignmentService.addAssignmentQuestion(assignmentId, questions));
    }

    @DeleteMapping("/question/remove")
    public NormalResult<?> removeAssignmentQuestion(@RequestParam("assignmentQuestionId") String assignmentQuestionId,HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        //如果令牌为空，返回错误信息
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        //获取身份
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        if (identity.contains("administrator")) {
            if (assignmentService.deleteAssignmentQuestion(assignmentQuestionId)) {
                return NormalResult.success();
            }
        }
        //如果身份是教师
        if (identity.contains("teacher")) {
            //根据令牌中的用户信息获取教师信息
            Teacher teacher = userMapper.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
            //根据assignmentQuestionId获取assignmentQuestion信息
            AssignmentQuestion assignmentQuestion = assignmentService.getAssignmentQuestionInfo(assignmentQuestionId);
            //获得对应的assignmentId
            String assignmentId = assignmentQuestion.getAssignmentId();
            //根据assignmentId获取assignment信息
            List<Assignment> assignments = assignmentService.assignmentInfo(List.of(assignmentId));
            //遍历assignments,如果assignment的teacherId等于当前教师的teacherId，则删除assignmentQuestion
            for (Assignment assignment : assignments) {
                if (assignment.getTeacherId().equals(teacher.getTeacherId())) {
                    if (assignmentService.deleteAssignmentQuestion(assignmentQuestionId)) {
                        return NormalResult.success();
                    }
                }
            }
        }
//        if (assignmentService.deleteAssignmentQuestion(assignmentQuestionId)) {
//            return NormalResult.success();
//        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/question/list")
    public NormalResult<?> assignmentQuestionList(@RequestParam("assignmentId") List<String> assignment) {
        return NormalResult.success(assignmentService.assignmentQuestionList(assignment));
    }

    @PatchMapping("/question/modify")
    public NormalResult<?> updateAssignmentQuestion(@RequestBody List<AssignmentQuestionInfoDto> dto) {
        List<String> list = assignmentService.updateAssignmentQuestion(dto);
        if (!list.isEmpty()) {
            return NormalResult.success(list);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

}
