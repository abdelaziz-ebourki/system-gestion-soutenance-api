package com.system_gestion_soutenance.api.admin.faculty.repository;

import com.system_gestion_soutenance.api.admin.faculty.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, String> {
    Optional<Faculty> findByName(String name);
}