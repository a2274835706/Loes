package edu.gdufs.llmobjectiveevaluationsystemspringserver.mapper;

import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.Assignment;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.AssignmentQuestion;
import edu.gdufs.llmobjectiveevaluationsystemspringserver.pojo.sql.ReleaseAssignment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AssignmentMapper {

    @Insert("insert into assignment values (#{assignment}, #{teacherId}, #{title}, #{description}, now(), now())")
    void addAssignment(String assignmentId, String teacherId, String title, String description);

    @Select("select * from assignment where assignment_id=#{assignment}")
    Assignment assignmentInfo(String assignmentId);

    @Select("select * from assignment where teacher_id=#{teacherId}")
    List<Assignment> assignmentOfTeacher(String teacherId);

    @Update("update assignment set title=#{title}, description=#{description}, update_at=now() where assignment_id=#{assignmentId}")
    void updateAssignment(String assignmentId, String title, String description);

    @Delete("delete from assignment where assignment_id=#{assignmentId}")
    void deleteAssignment(String assignmentId);

    @Insert("insert into assignment_question values (#{assignmentQuestionId}, #{assignmentId}, #{questionId}, #{score}, #{sortOrder})")
    void addAssignmentQuestion(String assignmentQuestionId, String assignmentId, String questionId, int score, int sortOrder);

    @Delete("delete from assignment_question where assignment_question_id=#{assignmentQuestionId}")
    void removeAssignmentQuestion(String assignmentQuestionId);

    @Select("select * from assignment_question where assignment_question_id=#{assignmentQuestionId}")
    AssignmentQuestion assignmentQuestionInfo(String assignmentQuestionId);

    @Select("select * from assignment_question where assignment_id=#{assignmentId} order by sort_order")
    List<AssignmentQuestion> assignmentQuestionList(String assignmentId);

    @Update("update assignment_question set score=#{score}, sort_order=#{sortOrder} where assignment_question_id=#{assignmentQuestionId}")
    void updateAssignmentQuestion(String assignmentQuestionId, int score, int sortOrder);

    @Insert("insert into release_assignment values (#{releaseId}, #{classID}, #{assignmentId}, #{releaseName}, #{description}, #{deadline}, now())")
    void addRelease(String releaseId, String classId, String assignmentId, String releaseName, String description, String deadline);

    @Delete("delete from release_assignment where release_id=#{releaseId}")
    void deleteRelease(String releaseId);

    @Update("update release_assignment set release_name=#{releaseName}, description=#{description}, deadline=#{deadline} where release_id=#{releaseId}")
    void updateRelease(String releaseId, String releaseName, String description, String deadline);

    @Select("select * from release_assignment where release_id=#{releaseId}")
    ReleaseAssignment releaseInfo(String releaseId);

    @Select("select * from release_assignment where class_id=#{classId}")
    List<ReleaseAssignment> releaseListOfClass(String classId);

    @Select("select * from release_assignment where assignment_id=#{assignment}")
    List<ReleaseAssignment> releaseListOfAssignment(String assignmentId);

}
