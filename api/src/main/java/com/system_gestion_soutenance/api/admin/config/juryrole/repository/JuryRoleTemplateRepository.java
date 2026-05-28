package com.system_gestion_soutenance.api.admin.config.juryrole.repository;

import com.system_gestion_soutenance.api.admin.config.juryrole.entity.JuryRoleTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JuryRoleTemplateRepository extends JpaRepository<JuryRoleTemplate, Long> {
    Optional<JuryRoleTemplate> findByName(String name);
}
