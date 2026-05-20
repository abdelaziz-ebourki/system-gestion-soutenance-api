package com.soutenance.repository;

import com.soutenance.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    boolean existsByCodeIgnoreCase(String code);
}
