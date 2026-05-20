package com.soutenance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "filieres", indexes = @Index(name = "idx_filieres_name", columnList = "name", unique = true))
public class Filiere extends BaseEntity {

    @Column(nullable = false, unique = true, length = 120)
    private String name;
}
