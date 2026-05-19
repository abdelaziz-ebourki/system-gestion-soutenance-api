package com.system_gestion_soutenance.api.user;

import com.system_gestion_soutenance.api.filiere.Filiere;
import com.system_gestion_soutenance.api.level.Level;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("STUDENT")
@PrimaryKeyJoinColumn(name = "id")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Student extends User {
    private String cne;
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;
}
