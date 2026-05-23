package com.system_gestion_soutenance.api.admin.department.repository;

import com.system_gestion_soutenance.api.admin.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
