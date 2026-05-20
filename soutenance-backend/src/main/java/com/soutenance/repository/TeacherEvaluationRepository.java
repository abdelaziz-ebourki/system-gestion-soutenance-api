package com.soutenance.repository;

import com.soutenance.entity.TeacherEvaluation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherEvaluationRepository extends JpaRepository<TeacherEvaluation, String> {
    List<TeacherEvaluation> findByTeacherId(String teacherId);
    List<TeacherEvaluation> findByProjectId(String projectId);
    long countByTeacherIdAndStatus(String teacherId, String status);
    void deleteByProjectId(String projectId);
}
