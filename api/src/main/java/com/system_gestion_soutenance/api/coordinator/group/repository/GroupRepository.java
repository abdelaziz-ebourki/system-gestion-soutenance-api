package com.system_gestion_soutenance.api.coordinator.group.repository;

import com.system_gestion_soutenance.api.coordinator.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, String> {
    List<Group> findByProjectId(String projectId);
}
