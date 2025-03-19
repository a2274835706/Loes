package edu.gdufs.llmobjectiveevaluationsystemspringserver.contorller;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.dto.ReleaseInfoDto;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.result.NormalResult;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/release")
public class ReleaseController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/add")
    public NormalResult<?> release(@RequestBody ReleaseInfoDto dto) {
        return NormalResult.success(assignmentService.addRelease(dto.getClassId(), dto.getAssignmentId(), dto.getReleaseName(), dto.getDescription(), dto.getDeadline()));
    }

    @DeleteMapping("/remove")
    public NormalResult<?> deleteRelease(@RequestParam("releaseId") String releaseId) {
        if (assignmentService.deleteRelease(releaseId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> modifyRelease(@RequestParam("releaseId") String releaseId, @RequestBody ReleaseInfoDto dto) {
        if (assignmentService.updateRelease(releaseId, dto.getReleaseName(), dto.getDescription(), dto.getDeadline())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/info")
    public NormalResult<?> releaseInfo(@RequestParam("releaseId") List<String> releaseId) {
        return NormalResult.success(assignmentService.releaseAssignmentInfo(releaseId));
    }

    @GetMapping("/{identity}/list")
    public NormalResult<?> releaseList(@PathVariable("identity") String identity, @RequestParam("id") List<String> id) {
        if (identity.equals("assignment")) {
            return NormalResult.success(assignmentService.releaseAssignmentOfAssignment(id));
        }
        if (identity.equals("class")) {
            return NormalResult.success(assignmentService.releaseAssignmentOfClass(id));
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

}
