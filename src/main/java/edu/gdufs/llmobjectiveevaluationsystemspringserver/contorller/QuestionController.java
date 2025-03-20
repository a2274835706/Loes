package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.QuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper.UserMapper;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Teacher;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.QuestionService;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/add")
    public NormalResult<?> addQuestion(@RequestBody QuestionInfoDto dto) {
        String questionId = questionService.addQuestion(dto.getTeacherId(), dto.getContent(), dto.getAnswer(), dto.getQuestionType());
        return NormalResult.success(questionId);
    }

    @GetMapping("/info")
    public NormalResult<?> questionInfo(@RequestParam("questionId") List<String> questionId) {
        return NormalResult.success(questionService.questionInfo(questionId));
    }

    @GetMapping("/list")
    public NormalResult<?> questionList(@RequestParam("teacherId") List<String> teacherId) {
        return NormalResult.success(questionService.questionList(teacherId));
    }

    @GetMapping("/search")
    public NormalResult<?> searchQuestion(@RequestParam("keyword") String keyword, @RequestParam("teacherId") String teacherId){
        List<Question> questions = questionService.searchQuestion(keyword, teacherId);
        if (questions != null && !questions.isEmpty()) {
            return NormalResult.success(questions);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @DeleteMapping("/delete")
    public NormalResult<?> deleteQuestion(@RequestParam("questionId") String questionId, HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        if(identity.contains("administrator")) {
            if (questionService.deleteQuestion(questionId)) {
                return NormalResult.success();
            }
        }
        if (identity.contains("teacher")){
            Teacher teacher = userMapper.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
            String currentTeacherId = teacher.getTeacherId();
            List<Question> questions = questionService.questionInfo(List.of(questionId));
            for(Question question : questions) {
                if(question.getTeacherId().equals(currentTeacherId)) {
                    if (questionService.deleteQuestion(questionId)) {
                        return NormalResult.success();
                    }
                }
            }
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> updateQuestion(@RequestBody QuestionInfoDto dto, HttpServletRequest request) {
        //获取令牌
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            return NormalResult.error(NormalResult.AUTHORIZED_ERROR);
        }
        String identity = jwtUtil.verifyToken(token).get("identity").toString();
        if(identity.contains("administrator")) {
            if (questionService.updateQuestion(dto.getTeacherId(), dto.getContent(), dto.getAnswer(), dto.getQuestionType())) {
                return NormalResult.success();
            }
        }
        if(identity.contains("teacher")) {
            Teacher teacher = userMapper.getTeacherByUserId((String) jwtUtil.verifyToken(token).get("userId"));
            String currentTeacherId = teacher.getTeacherId();
            if(dto.getTeacherId().equals(currentTeacherId)) {
                if (questionService.updateQuestion(dto.getTeacherId(), dto.getContent(), dto.getAnswer(), dto.getQuestionType())) {
                    return NormalResult.success();
                }
            }
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }



}
