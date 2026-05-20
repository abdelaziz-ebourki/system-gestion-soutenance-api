package com.soutenance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "juries",
    indexes = {
        @Index(name = "idx_juries_project", columnList = "project_id", unique = true),
        @Index(name = "idx_juries_president", columnList = "president_id"),
        @Index(name = "idx_juries_reporter", columnList = "reporter_id"),
        @Index(name = "idx_juries_examiner", columnList = "examiner_id")
    }
)
public class Jury extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, unique = true)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "president_id", nullable = false)
    private Teacher president;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Teacher reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examiner_id", nullable = false)
    private Teacher examiner;
}
