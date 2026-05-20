package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(
    name = "students",
    indexes = {
        @Index(name = "idx_students_cne", columnList = "cne", unique = true),
        @Index(name = "idx_students_filiere", columnList = "filiere_id"),
        @Index(name = "idx_students_level", columnList = "level_id")
    }
)
@PrimaryKeyJoinColumn(name = "user_id")
public class Student extends User {

    @Column(nullable = false, unique = true, length = 50)
    private String cne;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "level_id")
    private Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
}
