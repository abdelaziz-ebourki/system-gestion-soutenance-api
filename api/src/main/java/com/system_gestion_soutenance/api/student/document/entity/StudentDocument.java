package com.system_gestion_soutenance.api.student.document.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_document")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDocument {

    @Id
    private String id;

    @Column(name = "student_id", nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String deadline;

    @Column(nullable = false)
    private String status = "missing";

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "file_path")
    private String filePath;
}
