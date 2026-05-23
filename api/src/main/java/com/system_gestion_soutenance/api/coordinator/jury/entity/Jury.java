package com.system_gestion_soutenance.api.coordinator.jury.entity;

import com.system_gestion_soutenance.api.coordinator.project.entity.Project;
import com.system_gestion_soutenance.api.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jury")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jury {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "president_id", nullable = false)
    private Teacher president;

    @ManyToOne
    @JoinColumn(name = "reporter_id", nullable = false)
    private Teacher reporter;

    @ManyToOne
    @JoinColumn(name = "examiner_id", nullable = false)
    private Teacher examiner;
}
