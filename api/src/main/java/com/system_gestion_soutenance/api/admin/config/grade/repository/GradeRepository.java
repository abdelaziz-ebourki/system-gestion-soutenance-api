package com.system_gestion_soutenance.api.admin.config.grade.repository;

import com.system_gestion_soutenance.api.admin.config.grade.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, String> {
    Optional<Grade> findByName(String name);
}
