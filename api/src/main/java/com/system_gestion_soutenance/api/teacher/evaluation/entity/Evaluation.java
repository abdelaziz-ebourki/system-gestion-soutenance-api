package com.system_gestion_soutenance.api.teacher.evaluation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Evaluation {

    @Id
    private String id;

    @Column(name = "teacher_id", nullable = false)
    private String teacherId;

    @Column(name = "defense_session_id", nullable = false)
    private String defenseSessionId;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(nullable = false)
    private String role;

    private Double score;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false)
    private String status = "pending";

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;
}
