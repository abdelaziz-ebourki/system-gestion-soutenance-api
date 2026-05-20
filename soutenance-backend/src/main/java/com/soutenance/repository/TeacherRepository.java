package com.soutenance.repository;

import com.soutenance.entity.Teacher;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Optional<Teacher> findByEmailIgnoreCase(String email);
}
