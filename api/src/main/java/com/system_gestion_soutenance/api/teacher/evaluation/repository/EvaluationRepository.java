package com.system_gestion_soutenance.api.teacher.evaluation.repository;

import com.system_gestion_soutenance.api.teacher.evaluation.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, String> {
    List<Evaluation> findByTeacherId(String teacherId);
    List<Evaluation> findByProjectId(String projectId);
}
