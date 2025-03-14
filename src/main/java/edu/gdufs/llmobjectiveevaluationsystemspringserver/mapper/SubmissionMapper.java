package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Submission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SubmissionMapper {

    @Insert("insert into `submission`(`question_id`, `student_id`, `process`, `answer`, `score`, `feedback`, `submit_at`, `update_at`)" +
            " values (#{questionId}, #{studentId}, #{process}, #{answer}, 0, '', now(), now())")
    void addSubmission(long questionId, long studentId, String process, String answer);

    @Select("select * from `submission` where `question_id`=#{submissionId} and `student_id`=#{studentId}")
    Submission submissionInfo(long questionId, long studentId);

    @Select("select * from `submission` where `student_id`=#{studentId}")
    List<Submission> submissionOfStudent(long studentId);

    @Select("select * from `submission` where `question_id`=#{questionId}")
    List<Submission> submissionOfQuestion(long questionId);

    @Update("update `submission` set `process`=#{process}, `answer`=#{answer}, `update_at`=now() where `question_id`=#{questionId} and `student_id`=#{studentID}")
    void updateSubmission(long questionId, long studentId, String process, String answer);

    @Update("update `submission` set `score`=#{score}, `feedback`=#{feedback}, `correct_at`=now() where `question_id`=#{questionId} and `student_id`=#{studentId}")
    void correct(long questionId, long studentId, int score, String feedback);

    @Delete("delete from `submission` where `question_id`=#{questionId} and `student_id`=#{studentId}")
    void deleteSubmission(long questionId, long studentId);


}
