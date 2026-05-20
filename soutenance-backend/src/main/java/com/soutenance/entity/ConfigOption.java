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
@Table(
    name = "config_options",
    indexes = @Index(name = "idx_config_options_category_name", columnList = "category,name", unique = true)
)
public class ConfigOption extends BaseEntity {

    @Column(nullable = false, length = 80)
    private String category;

    @Column(nullable = false, length = 120)
    private String name;
}
