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
        long questionId = questionService.addQuestion(dto.getAssignmentId(), dto.getContent(), dto.getScore(), dto.getSortOrder(), dto.getQuestionType());
        if (questionId != -1) {
            return NormalResult.success(questionId);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/info")
    public NormalResult<?> questionInfo(@RequestParam("questionId") List<Long> questionId) {
        return NormalResult.success(questionService.questionInfo(questionId));
    }

    @GetMapping("/list")
    public NormalResult<?> questionList(@RequestParam("assignmentId") List<Long> assignmentId) {
        return NormalResult.success(questionService.questionList(assignmentId));
    }

    @DeleteMapping("/remove")
    public NormalResult<?> removeQuestion(@RequestParam("questionId") long questionId) {
        if (questionService.deleteQuestion(questionId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> modifyQuestion(@RequestBody QuestionInfoDto dto) {
        if (questionService.updateQuestion(dto.getQuestionId(), dto.getContent(), dto.getScore(), dto.getSortOrder(), dto.getQuestionType())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

}
