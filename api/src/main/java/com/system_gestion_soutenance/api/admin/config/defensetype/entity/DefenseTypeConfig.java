package com.system_gestion_soutenance.api.admin.config.defensetype.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "defense_type_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DefenseTypeConfig {

    @Id
    private String id;

    private boolean enabled;

    @Column(nullable = false)
    private String label;

    @Column(name = "label_plural")
    private String labelPlural;

    @Column(name = "default_duration")
    private int defaultDuration;

    @Column(name = "default_break")
    private int defaultBreak;
}
