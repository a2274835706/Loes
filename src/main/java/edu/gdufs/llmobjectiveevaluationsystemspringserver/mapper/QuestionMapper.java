package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question values (#{questionId}, #{teacherId}, #{content}, #{answer}, #{questionType}, now(), now())")
    void addQuestion(String questionId, String teacherId, String content, String answer, String questionType);

    @Select("select * from question where question_id=#{questionId}")
    Question questionInfo(String questionId);

    @Select("select * from question where teacher_id=#{teacherId}")
    List<Question> questionOfTeacher(String teacherId);

    @Select("select * from question where teacher_id=#{teacherId} and JSON_EXTRACT(content,'$.title') like CONCAT('%',#{keyword},'%')")
    List<Question> searchQuestion(String keyword, String teacherId);

    @Update("update question set content=#{content}, answer=#{answer}, question_type=#{questionType}, update_at=now() where question_id=#{questionId}")
    void updateQuestion(String questionId, String content, String answer, String questionType);

    @Delete("delete from question where question_id=#{questionId}")
    void deleteQuestion(String questionId);

}
