package com.system_gestion_soutenance.api.student.document.repository;

import com.system_gestion_soutenance.api.student.document.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentDocumentRepository extends JpaRepository<StudentDocument, String> {
    List<StudentDocument> findByStudentId(String studentId);
}
