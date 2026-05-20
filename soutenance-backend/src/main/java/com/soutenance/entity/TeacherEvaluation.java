package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "teacher_evaluations",
    indexes = {
        @Index(name = "idx_teacher_evaluations_teacher", columnList = "teacher_id"),
        @Index(name = "idx_teacher_evaluations_status", columnList = "status"),
        @Index(name = "idx_teacher_evaluations_defense", columnList = "defense_id")
    }
)
public class TeacherEvaluation extends BaseEntity {

    @Column(name = "defense_id", nullable = false, length = 80)
    private String defenseId;

    @Column(name = "project_title", nullable = false, length = 220)
    private String projectTitle;

    @Column(precision = 4, scale = 2)
    private BigDecimal score;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(nullable = false, length = 30)
    private String status = "pending";

    @Column(name = "submitted_at")
    private OffsetDateTime submittedAt;

    @Column(nullable = false, length = 30)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
