package com.system_gestion_soutenance.api.admin.config.defensetype.repository;

import com.system_gestion_soutenance.api.admin.config.defensetype.entity.DefenseTypeConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DefenseTypeConfigRepository extends JpaRepository<DefenseTypeConfig, String> {
}
