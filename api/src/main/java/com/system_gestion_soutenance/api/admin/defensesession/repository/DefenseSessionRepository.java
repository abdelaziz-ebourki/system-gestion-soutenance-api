package com.system_gestion_soutenance.api.admin.defensesession.repository;

import com.system_gestion_soutenance.api.admin.defensesession.entity.DefenseSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefenseSessionRepository extends JpaRepository<DefenseSession, Long> {
    List<DefenseSession> findByJuryRoleTemplate_Id(Long juryRoleTemplateId);
}
