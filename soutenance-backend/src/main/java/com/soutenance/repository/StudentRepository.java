package com.soutenance.repository;

import com.soutenance.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByEmailIgnoreCase(String email);
    boolean existsByCneIgnoreCase(String cne);
}
