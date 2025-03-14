package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.SubmissionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submission")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/add")
    public NormalResult<?> addSubmission(@RequestBody SubmissionInfoDto dto) {
        if (submissionService.addSubmission(dto.getQuestionId(), dto.getStudentId(), dto.getProcess(), dto.getAnswer())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/info")
    public NormalResult<?> submissionInfo(@RequestParam("questionId") long questionId,
                                          @RequestParam("studentId") long studentId) {
        return NormalResult.success(submissionService.submissionInfo(questionId, studentId));
    }

    @GetMapping("/{identity}/list")
    public NormalResult<?> submissionList(@PathVariable("identity") String identity, @RequestParam("id") List<Long> id) {
        if (identity.equals("question")) {
            return NormalResult.success(submissionService.submissionOfQuestion(id));
        }
        if (identity.equals("student")) {
            return NormalResult.success(submissionService.submissionOfStudent(id));
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> modify(@RequestBody SubmissionInfoDto dto) {
        if (submissionService.modifySubmission(dto.getQuestionId(), dto.getStudentId(), dto.getProcess(), dto.getAnswer())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/correct")
    public NormalResult<?> correct(@RequestParam("questionId") long questionId,
                                   @RequestParam("studentId") long studentId,
                                   @RequestParam("score") int score,
                                   @RequestParam("feedback") String feedback) {
        if (submissionService.correctAnswer(questionId, studentId, score, feedback)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @DeleteMapping("/remove")
    public NormalResult<?> remove(@RequestParam("questionId") long questionId,
                                  @RequestParam("studentId") long studentId) {
        if (submissionService.deleteSubmission(questionId, studentId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

}
