package com.soutenance.repository;

import com.soutenance.entity.StudentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDocumentRepository extends JpaRepository<StudentDocument, String> {
    long countByStatus(String status);
}
