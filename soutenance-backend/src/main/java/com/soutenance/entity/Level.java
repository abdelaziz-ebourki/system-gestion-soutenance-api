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
@Table(name = "levels", indexes = @Index(name = "idx_levels_name", columnList = "name", unique = true))
public class Level extends BaseEntity {

    @Column(nullable = false, unique = true, length = 80)
    private String name;
}
