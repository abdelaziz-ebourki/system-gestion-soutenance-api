package com.system_gestion_soutenance.api.admin.department.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system_gestion_soutenance.api.user.entity.Teacher;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "department")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @Id
    private String id;
    private String name;
    private String code;
    @ManyToOne
    @JoinColumn(name = "head_id")
    @JsonIgnore
    private Teacher head;

    public String getHeadId() {
        return head != null ? head.getId() : null;
    }
}
