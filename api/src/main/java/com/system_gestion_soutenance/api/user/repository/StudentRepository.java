package com.system_gestion_soutenance.api.user.repository;

import com.system_gestion_soutenance.api.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByMajorId(Long majorId);
    List<Student> findByLevelId(Long levelId);
}
