package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into `question` values (#{questionId}, #{assignmentId}, #{content}, #{score}, #{sortOrder}, #{questionType}, now(), now())")
    void addQuestion(long questionId, long assignmentId, String content, int score, int sortOrder, String questionType);

    @Select("select * from `question` where `question_id`=#{questionId}")
    Question questionInfo(long questionId);

    @Select("select * from `question` where `assignment_id`=#{assignmentId} order by `sort_order`")
    List<Question> questionList(long assignmentId);

    @Delete("delete from `question` where `question_id`=#{questionId}")
    void deleteQuestion(long questionId);

    @Update("update `question` set `content`=#{content}, `score`=#{score}, `sort_order`=#{sortOrder}, `question_type`=#{questionType}, update_at=now() " +
            "where `question_id`=#{questionId}")
    void updateQuestion(long questionId, String content, int score, int sortOrder, String questionType);

}
