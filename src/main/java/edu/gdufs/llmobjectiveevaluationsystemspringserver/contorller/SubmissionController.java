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
        String submissionId = submissionService.addSubmission(dto.getReleaseId(), dto.getStudentId(), dto.getQuestionId(), dto.getProcess(), dto.getAnswer());
        if (submissionId != null) {
            return NormalResult.success(submissionId);
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/info")
    public NormalResult<?> submissionInfo(@RequestParam("submissionId") List<String> submissionId) {
        return NormalResult.success(submissionService.submissionInfo(submissionId));
    }

    @DeleteMapping("/remove")
    public NormalResult<?> deleteSubmission(@RequestParam("submissionId") String submissionId) {
        if (submissionService.deleteSubmission(submissionId)) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @PatchMapping("/modify")
    public NormalResult<?> updateSubmission(@RequestBody SubmissionInfoDto dto) {
        if (submissionService.updateSubmission(dto.getSubmissionId(), dto.getProcess(), dto.getAnswer())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }

    @GetMapping("/{identity}/list")
    public NormalResult<?> submissionList(@PathVariable("identity") String identity, @RequestParam("id") List<String> id) {
        if (identity.equals("release")) {
            return NormalResult.success(submissionService.submissionOfRelease(id));
        }
        if (identity.equals("student")) {
            return NormalResult.success(submissionService.submissionOfStudent(id));
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    @GetMapping("/list/{identity}")
    public NormalResult<?> submissionListDetail(@PathVariable("identity") String identity, @RequestParam("idList") List<String> idList, @RequestParam("id") String id) {
        if (identity.equals("student")) {
            return NormalResult.success(submissionService.submissionOfReleaseOfStudents(idList, id));
        }
        if (identity.equals("release")) {
            return NormalResult.success(submissionService.submissionOfStudentOfReleases(id, idList));
        }
        return NormalResult.error(NormalResult.VALIDATION_ERROR);
    }

    @PatchMapping("/correct")
    public NormalResult<?> correct(@RequestBody SubmissionInfoDto dto) {
        if (submissionService.correctSubmission(dto.getSubmissionId(), dto.getScore(), dto.getFeedback())) {
            return NormalResult.success();
        }
        return NormalResult.error(NormalResult.EXISTENCE_ERROR);
    }


}
