package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "student_documents", indexes = @Index(name = "idx_student_documents_status", columnList = "status"))
public class StudentDocument extends BaseEntity {

    @Column(nullable = false, length = 180)
    private String name;

    @Column(nullable = false, length = 80)
    private String type;

    @Column(nullable = false)
    private LocalDate deadline;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "submitted_at")
    private OffsetDateTime submittedAt;
}
