package com.system_gestion_soutenance.api.coordinator.jury.repository;

import com.system_gestion_soutenance.api.coordinator.jury.entity.Jury;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JuryRepository extends JpaRepository<Jury, Long> {
    List<Jury> findByProjectId(Long projectId);
}
