package com.soutenance.repository;

import com.soutenance.entity.Jury;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuryRepository extends JpaRepository<Jury, String> {
    Optional<Jury> findByProjectId(String projectId);
    boolean existsByProjectId(String projectId);
    List<Jury> findByPresidentIdOrReporterIdOrExaminerId(String presidentId, String reporterId, String examinerId);
}
