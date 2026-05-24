package com.system_gestion_soutenance.api.user.repository;

import com.system_gestion_soutenance.api.user.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, String> {
    List<Teacher> findByGradeId(String gradeId);
    List<Teacher> findByDepartmentId(String departmentId);
}
