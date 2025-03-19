package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentQuestionInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/add")
    public NormalResult<?> addAssignment(AssignmentInfoDto dto) {
        return NormalResult.success(assignmentService.addAssignment(dto.getTeacherId(), dto.getTitle(), dto.getDescription()));
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
    public NormalResult<?> removeAssignment(@RequestParam("assignmentId") String assignmentId) {
        if (assignmentService.deleteAssignment(assignmentId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> updateAssignment(@RequestBody AssignmentInfoDto dto) {
        if (assignmentService.updateAssignment(dto.getAssignmentId(), dto.getTitle(), dto.getDescription())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PostMapping("/question/add")
    public NormalResult<?> addAssignmentQuestion(@RequestParam("assignmentId") String assignmentId,
                                                 @RequestBody List<AssignmentQuestionInfoDto> questions) {
        return NormalResult.success(assignmentService.addAssignmentQuestion(assignmentId, questions));
    }

    @DeleteMapping("/question/remove")
    public NormalResult<?> removeAssignmentQuestion(@RequestParam("assignmentQuestionId") String assignmentQuestionId) {
        if (assignmentService.deleteAssignmentQuestion(assignmentQuestionId)) {
            return NormalResult.success();
        }
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
