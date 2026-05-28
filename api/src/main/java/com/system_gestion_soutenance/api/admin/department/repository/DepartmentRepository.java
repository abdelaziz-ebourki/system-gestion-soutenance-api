package com.system_gestion_soutenance.api.admin.department.repository;

import com.system_gestion_soutenance.api.admin.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);
    List<Department> findByHead_Id(Long headId);
    List<Department> findByFaculty_Id(Long facultyId);
}
