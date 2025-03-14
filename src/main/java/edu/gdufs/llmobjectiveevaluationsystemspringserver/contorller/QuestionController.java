package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.QuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

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

    @DeleteMapping("/delete")
    public NormalResult<?> deleteQuestion(@RequestParam("questionId") String questionId) {
        if (questionService.deleteQuestion(questionId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> updateQuestion(@RequestBody QuestionInfoDto dto) {
        if (questionService.updateQuestion(dto.getTeacherId(), dto.getContent(), dto.getAnswer(), dto.getQuestionType())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }



}
