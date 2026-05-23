package com.system_gestion_soutenance.api.admin.defensesession.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.system_gestion_soutenance.api.admin.config.juryrole.entity.JuryRoleTemplate;
import com.system_gestion_soutenance.api.admin.session.entity.Session;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "defense_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefenseSession {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "global_session_id", nullable = false)
    @JsonIgnoreProperties("head")
    private Session globalSession;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "defense_type", nullable = false)
    private DefenseType defenseType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DefenseSessionStatus status = DefenseSessionStatus.DRAFT;

    @Column(name = "max_group_size")
    private int maxGroupSize;

    @Column(name = "defense_duration")
    private int defenseDuration;

    @Column(name = "break_duration")
    private int breakDuration;

    @Column(name = "submission_deadline")
    private LocalDate submissionDeadline;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "president", column = @Column(name = "coeff_president")),
            @AttributeOverride(name = "reporter", column = @Column(name = "coeff_reporter")),
            @AttributeOverride(name = "examiner", column = @Column(name = "coeff_examiner"))
    })
    private EvaluationCoefficients evaluationCoefficients;

    @ManyToOne
    @JoinColumn(name = "jury_role_template_id")
    @JsonIgnoreProperties("head")
    private JuryRoleTemplate juryRoleTemplate;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
}
