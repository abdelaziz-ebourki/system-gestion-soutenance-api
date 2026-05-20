package com.soutenance.repository;

import com.soutenance.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
    long countByStatus(String status);
    List<Project> findBySupervisorId(String supervisorId);
}
