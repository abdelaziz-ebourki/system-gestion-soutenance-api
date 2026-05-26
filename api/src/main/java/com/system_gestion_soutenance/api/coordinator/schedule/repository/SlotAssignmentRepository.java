package com.system_gestion_soutenance.api.coordinator.schedule.repository;

import com.system_gestion_soutenance.api.coordinator.schedule.entity.SlotAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SlotAssignmentRepository extends JpaRepository<SlotAssignment, String> {
    List<SlotAssignment> findByProjectId(String projectId);
}
