package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Submission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubmissionMapper {

    @Insert("insert into submission values (#{submissionId}, #{releaseId}, #{studentId}, #{questionId}, #{process}, #{answer}, 0, '', now(), now(), null)")
    void addSubmission(String submissionId, String releaseId, String studentId, String questionId, String process, String answer);

    @Select("select * from submission where submission_id=#{submissionId}")
    Submission submissionInfo(String submissionId);

    @Select("select * from submission where release_id=#{releaseId}")
    List<Submission> submissionOfRelease(String releaseId);

    @Select("select * from submission where student_id=#{studentId}")
    List<Submission> submissionOfStudent(String studentId);

    @Select("select * from submission where question_id=#{questionId}")
    List<Submission> submissionOfQuestion(String questionId);

    @Select("select * from submission where release_id=#{releaseId} and student_id=#{studentId}")
    List<Submission> submissionOfReleaseAndStudent(String releaseId, String studentId);

    @Delete("delete from submission where release_id=#{submissionId}")
    void deleteSubmission(String submissionId);

    @Update("update submission set process=#{process}, answer=#{answer}, update_at=now() where submission_id=#{submissionId}")
    void updateSubmission(String submissionId, String process, String answer);

    @Update("update submission set score=#{score}, feedback=#{feedback}, correct_at=now() where submission_id=#{submissionId}")
    void correctSubmission(String submissionId, int score, String feedback);


}
