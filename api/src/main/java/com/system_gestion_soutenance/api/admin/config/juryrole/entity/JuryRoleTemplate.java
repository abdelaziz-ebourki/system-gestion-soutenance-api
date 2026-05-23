package com.system_gestion_soutenance.api.admin.config.juryrole.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "jury_role_template")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JuryRoleTemplate {

    @Id
    private String id;

    @Column(nullable = false, unique = true)
    private String name;
}
