package com.soutenance.repository;

import com.soutenance.entity.Grade;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, String> {
    Optional<Grade> findByNameIgnoreCase(String name);
}
