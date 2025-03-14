package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;


import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.AssignmentInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/add")
    public NormalResult<?> addAssignment(@RequestBody AssignmentInfoDto dto) {
        long assignmentId = assignmentService.addAssignment(dto.getCourseId(), dto.getTeacherId(), dto.getTitle(),
                dto.getDescription(), LocalDateTime.ofEpochSecond(dto.getDeadline(), 0, ZoneOffset.UTC));
        if (assignmentId != -1) {
            return NormalResult.success(assignmentId);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/info")
    public NormalResult<?> assignmentInfo(@RequestParam("assignmentId") List<Long> assignmentId) {
        return NormalResult.success(assignmentService.assignmentInfo(assignmentId));
    }

    @GetMapping("/{identity}/list")
    public NormalResult<?> assignmentList(@PathVariable("identity") String identity, @RequestParam("id") List<Long> id) {
        if (identity.equals("course")) {
            return NormalResult.success(assignmentService.assignmentOfCourse(id));
        }
        if (identity.equals("teacher")) {
            return NormalResult.success(assignmentService.assignmentOfTeacher(id));
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> modifyAssignment(@RequestBody AssignmentInfoDto dto) {
        if (assignmentService.updateAssignment(dto.getAssignmentId(), dto.getTitle(), dto.getDescription(),
                LocalDateTime.ofEpochSecond(dto.getDeadline(), 0, ZoneOffset.UTC))) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @DeleteMapping("/remove")
    public NormalResult<?> removeAssignment(@RequestParam("assignment") long assignmentId) {
        if (assignmentService.deleteAssignment(assignmentId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

}
