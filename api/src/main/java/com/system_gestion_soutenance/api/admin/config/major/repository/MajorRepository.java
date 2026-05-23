package com.system_gestion_soutenance.api.admin.config.major.repository;

import com.system_gestion_soutenance.api.admin.config.major.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, String> {
    Optional<Major> findByName(String name);
}
