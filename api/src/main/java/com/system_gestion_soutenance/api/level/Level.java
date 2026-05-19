package com.system_gestion_soutenance.api.level;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "level")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Level {
    @Id
    private String id;
    private String name;
}
