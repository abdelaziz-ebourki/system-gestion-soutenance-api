package com.system_gestion_soutenance.api.coordinator.project.repository;

import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> {
}
