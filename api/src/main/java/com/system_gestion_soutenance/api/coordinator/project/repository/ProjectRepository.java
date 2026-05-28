package com.system_gestion_soutenance.api.coordinator.project.repository;

import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findBySupervisorId(Long supervisorId);
    List<Project> findByStudentsId(Long studentId);
}
